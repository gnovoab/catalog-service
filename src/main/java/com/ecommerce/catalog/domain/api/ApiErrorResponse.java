
//Namespace
package com.ecommerce.catalog.domain.api;

//Imports
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

/**
 * Class that represents the system response when an error ocur
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse extends ApiMessageResponse {

    //Fields
    private List<String> errors;


    //Constructors
    public ApiErrorResponse(HttpStatus status, String message) {
        super(status, message);
    }

    public ApiErrorResponse(HttpStatus status, String message, List<String> errors) {
        super(status, message);
        this.errors = errors;
    }

    public ApiErrorResponse(HttpStatus status, String message, String error) {
        super(status, message);
        errors = Arrays.asList(error);
    }




    //Getters and Setters
    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}