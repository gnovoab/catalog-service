
//Namespae
package com.ecommerce.catalog.exception;

import org.springframework.http.HttpStatus;

/**
 * Class that represents an exception when a resource is not found
 */
public class HttpClientException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private HttpStatus httpStatus;

    /**
     * Constructor
     * @param httpStatus
     * @param message
     * @param cause
     */
    public HttpClientException(HttpStatus httpStatus, String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }


    //Getters and Setters


    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
