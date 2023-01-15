package com.example.challengequintoimpacto.repositories;

import com.example.challengequintoimpacto.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso,Long> {




}
