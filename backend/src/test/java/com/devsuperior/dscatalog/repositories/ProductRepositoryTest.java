package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository repository;
	
	private long existingId;
	private long countTotalProducts;
	private long nonExistingId;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		countTotalProducts = 28L;
		nonExistingId = 1000l;
	}
	
	@Test
	public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
		
		Product product = Factory.createProduct();
		product.setId(null);;
		product = repository.save(product);
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1, product.getId());
	}
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExist() {
	
		repository.deleteById(existingId);
		Optional<Product> result = repository.findById(existingId);
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void findByIdShoudReturnNotEmptyOptionalWhenIdExist () {
		Optional<Product> result = repository.findById(existingId);
		Assertions.assertTrue(result.isPresent());
	}
	
	@Test
	public void findByIdShoudReturnEmptyOptionalWhenIdDoesNotExist () {
		Optional<Product> result = repository.findById(nonExistingId);
		Assertions.assertTrue(result.isEmpty());
	}
}
