
//Namespace
package com.ecommerce.catalog.domain.api;

//Imports
import org.springframework.http.HttpStatus;

/**
 * Class that represents the response messages when a 3rd party interact with out service
 */
public class ApiMessageResponse {

    //Fields
    private String message;
    private HttpStatus status;


    /**
     * Constructor
     */
    public ApiMessageResponse() { }


    /**
     * Constructor
     *
     * @param status
     * @param message
     */
    public ApiMessageResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }


    //Getters and Setters


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

}
