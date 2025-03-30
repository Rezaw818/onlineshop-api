package com.example.dataaccess.repository.site;


import com.example.dataaccess.entity.site.Nav;
import com.example.dataaccess.entity.site.Slider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SliderRepository extends JpaRepository<Slider, Long> {
    List<Slider> findAllByEnableIsTrueOrderByOrderNumberAsc();

    @Query("""
select orderNumber from Nav order by orderNumber desc limit 1
""")
    Integer findLastOrderNumber();

    Optional<Slider> findFirstByOrderNumberLessThanOrderByOrderNumberDesc(Integer orderNumber);

    Optional<Slider> findFirstByOrderNumberGreaterThanOrderByOrderNumberDesc(Integer orderNumber);


}
