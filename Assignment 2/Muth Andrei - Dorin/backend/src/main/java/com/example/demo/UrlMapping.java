package com.example.demo;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String BOOKS = API_PATH + "/book";
    public static final String ID = "/{id}";
    public static final String FIND_ALL = "/find-all";
    public static final String ADD_BOOK = "/add-book";
    public static final String UPDATE_BOOK = "/update-book/{id}";
    public static final String DELETE_BOOK = "/delete-book/{id}";
    public static final String FILTER_BOOKS = "/filter-books/{word}";
    public static final String SELL_BOOKS = "/sell-books/{id}";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";
    public static final String USERS = API_PATH + "/user";
    public static final String UPDATE_USER = "/update-user/{id}";

    public static final String DELETE_USER = "/delete-user/{id}";

    public static final String GENERATE_REPORT = "/generate-report/{type}";
}
