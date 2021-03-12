package com.javaspringboot.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaspringboot.data.ProductRepository;
import com.javaspringboot.entity.Product;

@Service
public class ProductService {

	private String NAME_NECESSARY = "Name is necessary.";
	private String DESCRIPTION_NECESSARY = "Description is necessary.";
	private String PRICE_NECESSARY = "Price is necessary.";
	private String PRICE_NEEDS_TO_BE_POSITIVE = "Price needs to be positive.";
	private String NAME_ALREADY_EXISTS = "Name already exists.";
	private String NAME_ALREADY_EXISTS_WITH_ANOTHER_PRODUCT = "Name already exists with another product.";

	@Autowired
	private ProductRepository productRepository;

	public ErrorResponse validateGeneral(Product product) {
		if (!product.hasName()) {
			return new ErrorResponse(NAME_NECESSARY);
		}
		if (!product.hasDescription()) {
			return new ErrorResponse(DESCRIPTION_NECESSARY);
		}
		if (!product.hasPrice()) {
			return new ErrorResponse(PRICE_NECESSARY);
		} 
		if (!product.hasPositivePrice()) {
			return new ErrorResponse(PRICE_NEEDS_TO_BE_POSITIVE);
		}
		return null;
	}

	public ErrorResponse validateCreate(Product product) {
		ErrorResponse response = validateGeneral(product);
		if (response != null) {
			return response;
		}

		Product productFindByName = productRepository.findByName(product.getName());
		if (productFindByName != null) {
			return new ErrorResponse(NAME_ALREADY_EXISTS);
		}

		return null;
	}

	public ErrorResponse validateUpdate(Long id, Product product) {
		ErrorResponse response = validateGeneral(product);
		if (response != null) {
			return response;
		}

		Product productFindByNameAndIdNot = productRepository.findByNameAndIdNot(product.getName(), id);
		if (productFindByNameAndIdNot != null) {
			return new ErrorResponse(NAME_ALREADY_EXISTS_WITH_ANOTHER_PRODUCT);
		}

		return null;
	}

}