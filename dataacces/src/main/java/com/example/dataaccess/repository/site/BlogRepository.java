package com.example.dataaccess.repository.site;


import com.example.dataaccess.entity.site.Blog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    Optional<Blog> findById(Long id);


    @Query("""
from Blog where status = com.example.dataaccess.enums.BlogStatus.Published
and publishDate <= current_date 
order by publishDate desc
""")
    List<Blog> findAllPublished(Pageable pageable);




}
