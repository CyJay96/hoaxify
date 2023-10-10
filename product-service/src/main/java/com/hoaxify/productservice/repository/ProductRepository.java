package com.hoaxify.productservice.repository;

import com.hoaxify.productservice.model.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
