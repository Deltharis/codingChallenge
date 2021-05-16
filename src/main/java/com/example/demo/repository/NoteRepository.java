package com.example.demo.repository;

import com.example.demo.repository.entities.CaseEntity;
import com.example.demo.repository.entities.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Integer> {
}
