package com.ecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.models.Product;
import com.ecommerce.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    // endpoint para listar todos los productos
    @GetMapping
    public ResponseEntity<List<Product>> products(){
        try {
            List<Product> products = productService.products();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // endpoint para buscar un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> productById(@PathVariable Integer id){
        try {
            Product product = productService.findProductById(id);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // endpoint para guardar un producto
    @PostMapping
    public ResponseEntity<Product> save(@RequestBody Product newProduct){
        try {
            Product product = productService.save(newProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(product); 
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // endpoint para actualizar un producto
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(
        @PathVariable Integer id, 
        @RequestBody Product product){
            try {
                Product UpdatedProduct = productService.update(id, product);
                return ResponseEntity.ok(UpdatedProduct);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }
    
    // endpoint para eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable Integer id){
        try {
            productService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // endpoint para filtrar productos por categoria y precio
    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterProducts(
        @RequestParam(required = false) String category,
        @RequestParam(required = false) Double minPrice,
        @RequestParam(required = false) Double maxPrice
    ){
        try {
            List<Product> products = productService.filter(category, minPrice, maxPrice);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // endpoint para buscar productos por nombre
    @GetMapping("/search")
    public ResponseEntity<List<Product>> filterByNameProducts(
        @RequestParam(required = false) String name){
            try {
                List<Product> products = productService.filterByName(name);
                return ResponseEntity.ok(products);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }
}
