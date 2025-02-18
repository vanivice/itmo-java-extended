package com.example.itmo.extended.model.db.repository;

import com.example.itmo.extended.model.db.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query(nativeQuery = true, value = "select * from cars where user_id = :userId")
    List<Car> getCarUser(@Param("userId") Long id);

    @Query("select  u from User u where u.lastName like %:filter% or u.firstName like %:filter%")
    Page<Car> findAllFiltered(Pageable pageRequest, @Param("filter") String filter);
}
