package software.amazonaws.example.product.dao;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import software.amazonaws.example.product.entity.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {
		
}