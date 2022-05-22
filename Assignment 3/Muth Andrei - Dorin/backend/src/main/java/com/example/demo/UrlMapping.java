package com.example.demo;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String APP = API_PATH + "/app";
    public static final String FIND_ALL = "/find-all";
    public static final String IMPORT_APPS = "/import-apps";
    public static final String ADD_APP = "/add-app";
    public static final String UPDATE_APP = "/update-app/{id}";
    public static final String DELETE_APP = "/delete-app/{id}";
    public static final String FILTERED = "/filtered";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";
    public static final String USER = API_PATH + "/user";
    public static final String UPDATE_USER = "/update-user/{id}";
    public static final String DELETE_USER = "/delete-user/{id}";

    public static final String REVIEW = API_PATH + "/review";
    public static final String ADD_REVIEW = "/add-review";
    public static final String DOWNLOAD = "/download/{id}";
    public static final String GET_REVIEWS = "/get-reviews/{id}";

    public static final String THREAD = API_PATH + "/thread";
    public static final String ADD_THREAD = "/add-thread";
    public static final String GET_COMMENTS = "/get-comments/{id}";
    public static final String POST_COMMENT = "/post-comment";
    public static final String SUBSCRIBE = "/subscribe";
    public static final String UPDATE_USERS_COMMENTS = "/update-users-comments";

}
