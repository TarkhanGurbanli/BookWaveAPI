package com.tarkhan.backend.constants;

public final class Constants {

    private Constants() {
        // Private constructor to prevent instantiation
    }

    // HTTP Status Codes
    public static final String STATUS_CREATED = "201";
    public static final String STATUS_OK = "200";
    public static final String STATUS_NO_CONTENT = "204";

    // Success Messages
    public static final String MESSAGE_CREATED_SUCCESSFULLY = "Resource created successfully.";
    public static final String MESSAGE_LOGIN_SUCCESSFUL = "Login successful.";
    public static final String MESSAGE_REGISTER_SUCCESSFUL = "Registration successful.";
    public static final String MESSAGE_UPDATE_SUCCESSFUL = "Resource updated successfully.";
    public static final String MESSAGE_DELETE_SUCCESSFUL = "Resource deleted successfully.";
    public static final String MESSAGE_ROLE_GRANTED = "Role granted successfully.";

    // Error Messages (Optional for handling various error cases)
    public static final String ERROR_INTERNAL_SERVER = "An unexpected error occurred. Please try again later.";
    public static final String ERROR_BAD_REQUEST = "The request could not be understood or was missing required parameters.";
    public static final String ERROR_UNAUTHORIZED = "Authentication is required and has failed or has not been provided.";
    public static final String ERROR_FORBIDDEN = "You do not have permission to access this resource.";
    public static final String ERROR_NOT_FOUND = "The requested resource was not found.";
}
