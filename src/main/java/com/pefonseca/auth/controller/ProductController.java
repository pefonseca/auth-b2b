package com.pefonseca.auth.controller;

import com.pefonseca.auth.domain.product.Product;
import com.pefonseca.auth.domain.product.ProductRequestDTO;
import com.pefonseca.auth.domain.product.ProductResponseDTO;
import com.pefonseca.auth.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> postProduct(@Valid @RequestBody ProductRequestDTO requestDTO) {
        Product newProduct = new Product(requestDTO);

        this.productRepository.save(newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> productList = this.productRepository.findAll().stream().map(ProductResponseDTO::new).toList();
        return ResponseEntity.ok(productList);
    }

}
