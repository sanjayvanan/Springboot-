package com.web_crud.pjct1.Repository;

import org.springframework.data.repository.CrudRepository;

import com.web_crud.pjct1.Model.Product;

public interface ProductRepository extends CrudRepository<Product,Integer> {
	Long countById(Integer id);

}
