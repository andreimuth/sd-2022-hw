package com.example.demo.app;

import com.example.demo.app.dto.AppDto;
import com.example.demo.app.dto.AppFilterRequestDto;
import com.example.demo.review.ReviewService;
import com.example.demo.review.dto.UIReviewDto;
import com.example.demo.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.io.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.example.demo.UrlMapping.*;

@RestController
@RequestMapping(APP)
@RequiredArgsConstructor
public class AppController {

    private final AppService appService;
    private final ReviewService reviewService;

    @GetMapping(FIND_ALL)
    public List<AppDto> findAllApps() {
        return appService.findAll();
    }

    @GetMapping(IMPORT_APPS)
    public ResponseEntity<?> importApps() {
        System.out.println("importing apps...");
        try {
            return ResponseEntity.ok().body(appService.importApps());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping(ADD_APP)
    public AppDto create(@RequestBody AppDto app) {
        return appService.create(app);
    }

    @PutMapping(UPDATE_APP)
    public AppDto update(@PathVariable Long id, @RequestBody AppDto app) {
        return appService.update(id, app);
    }

    @DeleteMapping(DELETE_APP)
    public void delete(@PathVariable Long id) {
        appService.delete(id);
    }

    @GetMapping(FILTERED)
    public List<AppDto> filteredApps(@ModelAttribute("filter") AppFilterRequestDto filter) {
        return appService.findAllFiltered(filter);
    }

    @GetMapping(DOWNLOAD)
    public void download(HttpServletResponse response, @PathVariable Long id) {
        File file = appService.generatePDF(id);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        try (InputStream inputStream = new FileInputStream(file)) {
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(GET_REVIEWS)
    public List<UIReviewDto> getReviewsForApp(@PathVariable Long id) {
        return reviewService.getReviewsForApp(id);
    }

    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    //@ExceptionHandler(MethodArgumentNotValidException.class)
    //public ResponseEntity<MessageResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
    //    return ResponseEntity
    //            .badRequest()
    //            .body(new MessageResponse(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage()));
    //}

}
