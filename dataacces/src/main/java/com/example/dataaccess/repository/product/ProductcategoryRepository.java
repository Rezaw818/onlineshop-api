package com.example.dataaccess.repository.product;


import com.example.dataaccess.entity.product.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductcategoryRepository extends JpaRepository<ProductCategory, Long> {

   List<ProductCategory> findAllByEnableIsTrue();
}
