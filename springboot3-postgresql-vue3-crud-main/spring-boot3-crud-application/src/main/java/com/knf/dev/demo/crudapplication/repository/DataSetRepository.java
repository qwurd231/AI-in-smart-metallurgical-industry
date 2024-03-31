package com.knf.dev.demo.crudapplication.repository;

import com.knf.dev.demo.crudapplication.entity.DataSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataSetRepository extends JpaRepository<DataSet, Long> {
    @Query("select d from DataSet d where d.userId = :userId")
    List<DataSet> findAllByUserId(@Param("userId") Long userId);
}
