package com.javaspringboot.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.javaspringboot.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Product findByName(String name);

	Product findByNameAndIdNot(String name, Long Id);

	@Query("SELECT product FROM Product product WHERE 1=1 " 
			+ " and (:name IS NULL OR product.name LIKE %:name%)"
			+ " and (:description IS NULL OR product.description LIKE %:description%)"
			+ " and (:maxPrice IS NULL OR product.price <= :maxPrice)"
			+ " and (:minPrice IS NULL OR product.price >= :minPrice)")
	List<Product> findByOptionalNameOptionalDescriptionOptionalPrice(@Param("name") Optional<String> name,
			@Param("description") Optional<String> description, @Param("minPrice") Optional<Float> minPrice,
			@Param("maxPrice") Optional<Float> maxPrice);

}