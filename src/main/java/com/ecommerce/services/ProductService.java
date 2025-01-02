package com.ecommerce.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.DatabaseException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.models.Product;
import com.ecommerce.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // metodo para listar todos los productos
    public List<Product> products(){
        try {
            return productRepository.findAll();
        } catch (DataAccessException e) {
            throw new DatabaseException("Database error while listing products");
        }
    }

    // metodo para buscar un producto por ID
    public Product findProductById(Integer id){
        try {
            return productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("product with ID " + id + "not found"));
        } catch (DataAccessException e) {
            throw new DatabaseException("Database error while finding product by ID");
        }
    }

    // metodo para guardar un producto
    public Product save(Product product){
        try {
            return productRepository.save(product);
        } catch (DataAccessException e) {
            throw new DatabaseException("error saving the product");
        }
    }

    // metodo para actualizar un producto
    public Product update(Integer id, Product product){
        try {
            Product productFound = findProductById(id);

            productFound.setName(product.getName());
            productFound.setDescription(product.getDescription());
            productFound.setPrice(product.getPrice());
            productFound.setStock(product.getStock());
            productFound.setCategory(product.getCategory());
            productFound.setCreatedAt(product.getCreatedAt());

            return save(productFound);
            
        } catch (DataAccessException e) {
            throw new DatabaseException("error updating the product");
        }
    }

    // metodo para eliminar un producto
    public void delete(Integer id){
        try {
            Product productFound = findProductById(id);

            productRepository.deleteById(productFound.getIdProduct());
        } catch (DataAccessException e) {
            throw new DatabaseException("error deleting the product");
        }
    }

    // metodo para filtrar productos por categoria y precio
    public List<Product> filter(String category, Double minPrice, Double maxPrice){
        try {
            if(Objects.nonNull(category)){
                if (Objects.nonNull(minPrice) && Objects.nonNull(maxPrice)) {
    
                    return productRepository.findByCategoryAndPriceBetween(category, minPrice, maxPrice);
    
                }else if(Objects.isNull(minPrice) && Objects.nonNull(maxPrice)){
    
                    return productRepository.findByCategoryAndPriceLessThanEqual(category, maxPrice);
    
                }else if(Objects.nonNull(minPrice) && Objects.isNull(maxPrice)){
    
                    return productRepository.findByCategoryAndPriceGreaterThanEqual(category, minPrice);
                }else {
                    return productRepository.findByCategory(category);
                }
            }else {
    
                    if (Objects.nonNull(minPrice) && Objects.nonNull(maxPrice)) {
    
                        return productRepository.findByPriceBetween(minPrice, maxPrice);
    
                    }else if(Objects.isNull(minPrice) && Objects.nonNull(maxPrice)){
    
                        return productRepository.findByPriceLessThanEqual(maxPrice);
    
                    }else if(Objects.nonNull(minPrice) && Objects.isNull(maxPrice)){
    
                        return productRepository.findByPriceGreaterThanEqual(minPrice);
                    }
                }
            
            return productRepository.findAll();
                    
        } catch (DataAccessException e) {
            throw new DatabaseException("error filtering the products");
        }
    }

    // metodo para filtrar productos por nombre
    public List<Product> filterByName(String name){
        try {
            if (Objects.nonNull(name)) {
                return productRepository.findByNameContaining(name);
            }
            return productRepository.findAll();
        } catch (DataAccessException e) {
            throw new DatabaseException("error flitering by name the products");
        }
    }



}
