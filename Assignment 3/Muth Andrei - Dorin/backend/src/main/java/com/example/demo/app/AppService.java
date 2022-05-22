package com.example.demo.app;

import com.example.demo.app.dto.ApiCallAppDto;
import com.example.demo.app.dto.AppDto;
import com.example.demo.app.dto.AppFilterRequestDto;
import com.example.demo.app.mapper.AppMapper;
import com.example.demo.app.model.App;
import com.example.demo.app.model.AppType;
import com.example.demo.app.model.EType;
import com.example.demo.app.model.PriceOverview;
import com.example.demo.pdf.PDFGenerator;
import com.example.demo.response.ApiResponse;
import com.example.demo.response.AppDetailsApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.example.demo.app.model.EType.OTHER;
import static java.util.Optional.ofNullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppService {

    private final AppRepository appRepository;
    private final TypeRepository typeRepository;
    private final ObjectMapper objectMapper;
    private final AppMapper appMapper;
    private List<ApiCallAppDto> apiApps;
    private final PDFGenerator pdfGenerator;

    public List<AppDto> findAll() {
        List<AppDto> collect = appRepository.findAll().stream()
                .map(app -> {
                    AppDto appDto = appMapper.toDto(app);
                    appMapper.populateTypeInDto(app, appDto);
                    return appDto;
                }).collect(Collectors.toList());
        return collect;
    }

    public List<AppDto> importApps() throws JsonProcessingException {
        if(apiApps == null) {
            String uri = "https://api.steampowered.com/ISteamApps/GetAppList/v0002/?key=STEAMKEY&format=json";
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(uri, String.class);
            ApiResponse apiResponse = objectMapper.readValue(response, ApiResponse.class);
            if(apiResponse == null) {
                return List.of();
            }
            apiApps = apiResponse.getApplist().getApps();
        }
        List<ApiCallAppDto> filteredItems = new ArrayList<>();
        List<AppDetailsApiResponse> appDetailsApiResponses = apiApps.subList(0, 100).stream() // from what I've read on the internet
                .map(app -> {                                              //the api for app details is limited to 200 requests in 5 minutes
                    try {                                                  //so I didn't have a better idea how to do this in order to
                        filteredItems.add(app);                           //get the data I need
                        AppDetailsApiResponse appDetailsApiResponse = callAppDetailsApi(app);
                        if(appDetailsApiResponse.getSuccess()) {
                            PriceOverview priceOverview = appDetailsApiResponse.getData().getPriceOverview();
                            ofNullable(priceOverview).ifPresent(po -> po.setPrice(po.getUnformattedPrice() / 100.0F));
                        }
                        return appDetailsApiResponse;
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }).filter(AppDetailsApiResponse::getSuccess)
                .collect(Collectors.toList());
        apiApps.removeAll(filteredItems);
        return appRepository.saveAll(appDetailsApiResponses.stream().map(app -> {
                    App toSave = appMapper.fromAppDetailsDto(app.getData());
                    appMapper.populatePrice(app.getData(), toSave);
                    AppType type = findTypeByName(app.getData().getType());
                    appMapper.populateTypeInResource(type, toSave);
                    return toSave;
                })
                .collect(Collectors.toList())).stream().map(appMapper::toDto).collect(Collectors.toList());
    }

    public AppDetailsApiResponse callAppDetailsApi(ApiCallAppDto app) throws JsonProcessingException {
        Long id = app.getId();
        String uri = "https://store.steampowered.com/api/appdetails?appids=" + id;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        assert result != null;
        String appDetails = result.substring(4 + id.toString().length(), result.length() - 1);
        return objectMapper.readValue(appDetails, AppDetailsApiResponse.class);
    }

    public AppType findTypeByName(String type) {
        try {
            EType eType = EType.valueOf(type.toUpperCase());
            return typeRepository.findByName(eType).orElseThrow(() -> new RuntimeException("Type " + type + " not found"));
        } catch (IllegalArgumentException e) {
            return typeRepository.findByName(OTHER).orElseThrow(() -> new RuntimeException("Cannot find other type"));
        }
    }

    public AppDto create(AppDto appDto) {
        App app = appMapper.fromDto(appDto);
        AppType type = findTypeByName(appDto.getType());
        appMapper.populateTypeInResource(type, app);
        AppDto dto = AppDto.builder().name(app.getName()).build();
        System.out.println(dto.getName());
        return appMapper.toDto(appRepository.save(app));
    }

    public AppDto update(Long id, AppDto appDto) {
        App app = findById(id);
        app.setName(appDto.getName());
        app.setType(findTypeByName(appDto.getType()));
        app.setDescription(appDto.getDescription());
        app.setPrice(appDto.getPrice());
        return appMapper.toDto(appRepository.save(app));
    }

    public App findById(Long id) {
        return appRepository.findById(id).orElseThrow(() -> new RuntimeException("App with id = " + id + " not found"));
    }

    public void delete(Long id) {
        appRepository.deleteById(id);
    }

    public List<AppDto> findAllFiltered(AppFilterRequestDto filter) {
        AppType type = null;
        if (filter.getType() != null) {
            type = findTypeByName(filter.getType());
        }
        return appRepository.findAll(
                AppSpecifications.specificationsFromFilter(filter, type)
        ).stream().map(app -> {
            AppDto appDto = appMapper.toDto(app);
            appMapper.populateTypeInDto(app, appDto);
            return appDto;
        }).collect(Collectors.toList());
    }

    public File generatePDF(Long id) {
        return pdfGenerator.exportPDF(findById(id));
    }

}
