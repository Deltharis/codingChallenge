package com.example.demo.repository;

import com.example.demo.model.Case;
import com.example.demo.repository.entities.CaseEntity;
import com.example.demo.repository.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaseRepository extends JpaRepository<CaseEntity, Integer> {


    @Query(value = "SELECT c FROM CaseEntity c LEFT JOIN FETCH c.user LEFT JOIN FETCH c.notes " +
            "WHERE c.status = :status")
    List<CaseEntity> findAllByStatus(@Param("status") Case.Status status);

    //todo add join fetch
    List<CaseEntity> findAllByUser(UserEntity userEntity);

    //todo add join fetch
    List<CaseEntity> findAllByUserAndStatus(UserEntity user, Case.Status status);
}
