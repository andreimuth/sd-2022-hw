package com.example.demo.app;

import com.example.demo.app.dto.AppFilterRequestDto;
import com.example.demo.app.model.App;
import com.example.demo.app.model.AppType;
import org.springframework.data.jpa.domain.Specification;

import java.util.concurrent.atomic.AtomicReference;

import static java.util.Optional.ofNullable;

public class AppSpecifications {

    public static Specification<App> similarNames(String name) {
        return (root, query, cb) -> cb.like(root.get("name"), "%" + name + "%");
    }



    public static Specification<App> similarDescription(String description) {
        return (root, query, cb) -> cb.like(root.get("description"), "%" + description + "%");
    }

    public static Specification<App> smallerPrice(Float price) {
        return (root, query, cb) -> cb.lessThan(root.get("price"), price);
    }

    public static Specification<App> type(AppType type) {
        return (root, query, cb) -> cb.equal(root.get("type"), type);
    }

    public static Specification<App> specificationsFromFilter(AppFilterRequestDto filter, AppType type) {
        AtomicReference<Specification<App>> spec = new AtomicReference<>(((root, query, cb) -> cb.and()));
        ofNullable(filter.getName()).ifPresent(name -> spec.set(spec.get().and(similarNames(name))));
        if(!filter.getMaxPrice().isNaN()) {
            spec.set(spec.get().and(smallerPrice(filter.getMaxPrice())));
        }
        ofNullable(type).ifPresent(t -> spec.set(spec.get().and(type(t))));
        ofNullable(filter.getDescription()).ifPresent(description -> spec.set(spec.get().and(similarDescription(description))));
        return spec.get();
    }

}
