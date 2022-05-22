package com.example.demo.app.mapper;

import com.example.demo.app.dto.AppDetailsDto;
import com.example.demo.app.dto.AppDto;
import com.example.demo.app.model.App;
import com.example.demo.app.model.AppType;
import org.mapstruct.*;

import static java.util.Optional.ofNullable;

@Mapper(componentModel = "spring")
public interface AppMapper {

    @Mappings({
            @Mapping(target = "type", ignore = true)
    })
    AppDto toDto(App app);

    @Mappings({
            @Mapping(target = "type", ignore = true)
    })
    App fromDto(AppDto appDto);

    @Mappings({
            @Mapping(target = "price", ignore = true),
            @Mapping(target = "type", ignore = true)
    })
    App fromAppDetailsDto(AppDetailsDto appDetailsDto);

    @AfterMapping
    default void populatePrice(AppDetailsDto appDetailsDto, @MappingTarget App app) {
        ofNullable(appDetailsDto.getPriceOverview()).ifPresentOrElse(
                priceOverview -> app.setPrice(priceOverview.getPrice()),
                () -> app.setPrice(0.0f));
    }

    @AfterMapping
    default void populateTypeInResource(AppType type, @MappingTarget App app) {
        app.setType(type);
    }

    @AfterMapping
    default void populateTypeInDto(App app, @MappingTarget AppDto appDto) {
        appDto.setType(app.getType().getName().name());
    }

}