
//Namespace
package com.ecommerce.catalog.controller;

//Imports

import com.ecommerce.catalog.domain.api.ApiErrorResponse;
import com.ecommerce.catalog.domain.api.ApiMessageResponse;
import com.ecommerce.catalog.domain.model.Product;
import com.ecommerce.catalog.service.ProductService;
import com.ecommerce.catalog.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Endpoint for Products
 */
@Tag(name = "PRODUCT", description = "Everything regarding product catalog operations")
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    //Fields
    @Autowired
    private transient ProductService productService;

    @Autowired
    private transient StockService stockService;


    /**
     * Fetch active products
     * @return
     */
    @Operation(summary = "Fetch active products", description = "Active products", tags = { "product" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Product.class)))),
            @ApiResponse(responseCode = "500", description = "The service encountered a problem.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Product>> fetchActiveProducts() {

        //Retrieve active products
        Iterable<Product> products = productService.fetchActiveProducts();

        //Return the active products
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    /**
     * Fetch products
     * @return
     */
    @Operation(summary = "Fetch all products", description = "All products", tags = { "product" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Product.class)))),
            @ApiResponse(responseCode = "500", description = "The service encountered a problem.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Product>> fetchProducts() {

        //Retrieve products
        Iterable<Product> products = productService.fetchProducts();

        //Return the products
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    /**
     * Get specific product
     * @param id
     * @return
     */
    @Operation(summary = "Retrieve a product", description = "Get a product", tags = { "product" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "400", description = "Malformed request syntax", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "The service encountered a problem.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> fetchProduct(@PathVariable @Valid Long id) {

        //Retrieve a product
        Product product = productService.findProduct(id);



        //Return the product requested
        return new ResponseEntity<>(product, HttpStatus.OK);
    }



    /**
     * Create product
     * @return
     */
    @Operation(summary = "Create a new product", description = "New product", tags = { "product" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Resource created", content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "400", description = "Malformed request syntax", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "The service encountered a problem.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product) {

        //Create product
        Product productCreated = productService.save(product);

        //Update stock
        stockService.createProductStock(productCreated);

        //Return the product created
        return new ResponseEntity<>(productCreated, HttpStatus.CREATED);
    }


    /**
     * Update product
     * @return
     */
    @Operation(summary = "Update a product", description = "Update a product", tags = { "product" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "400", description = "Malformed request syntax", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "The service encountered a problem.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable @Valid Long id, @RequestBody @Valid Product product) {

        //Assign Id
        product.setId(id);

        //Update product
        Product productUpdated = productService.save(product);

        //Return the product ypdated
        return new ResponseEntity<>(productUpdated, HttpStatus.OK);
    }


    /**
     * Delete product
     * @return
     */
    @Operation(summary = "Delete a product", description = "Delete a product", tags = { "product" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ApiMessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Malformed request syntax", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "The service encountered a problem.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiMessageResponse> deleteProduct(@PathVariable @Valid Long id) {

        //Delete product
        productService.delete(id);

        //Return the message
        return new ResponseEntity<>(new ApiMessageResponse(HttpStatus.OK, "Product Deleted"), HttpStatus.OK);
    }
}
