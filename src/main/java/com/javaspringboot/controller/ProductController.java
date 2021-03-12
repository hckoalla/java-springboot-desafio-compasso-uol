package com.javaspringboot.controller;

import java.util.List;
import java.util.Optional;

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

import com.javaspringboot.core.ErrorResponse;
import com.javaspringboot.core.ProductService;
import com.javaspringboot.data.ProductRepository;
import com.javaspringboot.entity.Product;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductRepository productRepository;

	@PostMapping
	public ResponseEntity<?> save(@RequestBody Product newProduct) {
		ErrorResponse response = productService.validateCreate(newProduct);
		if (response != null) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(newProduct));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Product newProduct) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		if (!optionalProduct.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		ErrorResponse response = productService.validateUpdate(id, newProduct);
		if (response != null) {
			return ResponseEntity.badRequest().body(response);
		}

		Product oldProduct = optionalProduct.get();
		oldProduct.setName(newProduct.getName());
		oldProduct.setDescription(newProduct.getDescription());
		oldProduct.setPrice(newProduct.getPrice());

		return ResponseEntity.ok(productRepository.save(oldProduct));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		if (!optionalProduct.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(optionalProduct.get());
	}

	@GetMapping
	public ResponseEntity<List<Product>> list() {
		return ResponseEntity.ok(productRepository.findAll());
	}

	@GetMapping("/search")
	public ResponseEntity<List<Product>> search(@RequestParam(value = "q", required = false) String nameDescription,
			@RequestParam(value = "min_price", required = false) Float minPrice,
			@RequestParam(value = "max_price", required = false) Float maxPrice) {
		return ResponseEntity.ok(productRepository.findByOptionalNameOptionalDescriptionOptionalPrice(
				Optional.ofNullable(nameDescription), Optional.ofNullable(nameDescription),
				Optional.ofNullable(minPrice), Optional.ofNullable(maxPrice)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		if (!optionalProduct.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		productRepository.delete(optionalProduct.get());
		return ResponseEntity.ok().build();
	}

}