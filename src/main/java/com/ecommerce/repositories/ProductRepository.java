package com.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ecommerce.models.Product;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // metodo para buscar productos entre un rango de precios
    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    // metodo para buscar productos con un precio mayor o igual a un valor
    List<Product> findByPriceLessThanEqual(double maxPrice);

    // metodo para buscar productos con un precio menor o igual a un valor
    List<Product> findByPriceGreaterThanEqual(double minPrice);

    // metodo para buscar productos por categoria
    List<Product> findByCategory(String category);

    // metodo para buscar productos por categoria y precio entre un rango
    List<Product> findByCategoryAndPriceBetween(String category, double minPrice, double maxPrice);

    // metodo para buscar productos por categoria y precio mayor o igual a un valor
    List<Product> findByCategoryAndPriceLessThanEqual(String category, double maxPrice);

    // metodo para buscar productos por categoria y precio menor o igual a un valor
    List<Product> findByCategoryAndPriceGreaterThanEqual(String category, double minPrice);

    // metodo para buscar productos por nombre
    List<Product> findByNameContaining(String name);
}
