package com.example.dataaccess.repository.product;


import com.example.dataaccess.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
from Product where enable = true and exist = true 
order by visitCount desc 
limit 6
""")
    List<Product> find6PopularProduct();

    @Query("""
from Product where enable = true and exist = true 
order by addDate desc 
limit 6
""")
    List<Product> find6NewstProduct();

    @Query("""
from Product where enable = true and exist = true 
order by price asc 
limit 6
""")
    List<Product> find6CheapestProduct();

    @Query("""
from Product where enable = true and exist = true 
order by price desc 
limit 6
""")
    List<Product> find6ExpensiveProduct();


    Optional<Product> findById(Long id);

    Page<Product> findAllByCategory_id(Long categoryId, Pageable pageable);
}
