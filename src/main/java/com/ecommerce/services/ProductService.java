package com.ecommerce.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        } catch (Exception e) {
            throw new RuntimeException("error listing products", e);
        }
    }

    // metodo para buscar un producto por ID
    public Product findProductById(Integer id){
        try {
            return productRepository.findById(id).orElseThrow(()->new RuntimeException("product with ID " + id + "not found"));
        } catch (Exception e) {
            throw new RuntimeException("error fiding product by ID", e);
        }
    }

    // metodo para guardar un producto
    public Product save(Product product){
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("error saving the product", e);
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
            
        } catch (Exception e) {
            throw new RuntimeException("error updating the product", e);
        }
    }

    // metodo para eliminar un producto
    public void delete(Integer id){
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("error deleting the product", e);
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
                    
        } catch (Exception e) {
            throw new RuntimeException("error filtering the products", e);
        }
    }

    // metodo para filtrar productos por nombre
    public List<Product> filterByName(String name){
        try {
            if (Objects.nonNull(name)) {
                return productRepository.findByNameContaining(name);
            }
            return productRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("error flitering by name the products", e);
        }
    }



}
