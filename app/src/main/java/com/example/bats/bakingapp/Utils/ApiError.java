package com.example.bats.bakingapp.Utils;


/**
 * Error class to be used with ErrorUtils class to return the correct parsed error type if retrofit
 * error
 */
public class ApiError {

    private int statusCode;
    private String endpoint;
    private String message = "Unknown error.";

    public int getStatusCode() { return statusCode; }

    public String getEndpoint() { return endpoint; }

    public String getMessage() { return message; }


}
