
//Namespace
package com.ecommerce.catalog.repository;

//Imports
import com.ecommerce.catalog.domain.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    Iterable<Product> findByActiveTrue();
}
