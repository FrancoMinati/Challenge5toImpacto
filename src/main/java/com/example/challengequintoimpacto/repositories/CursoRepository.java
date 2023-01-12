package com.example.challengequintoimpacto.repositories;
import com.example.challengequintoimpacto.entities.Alumno;
import com.example.challengequintoimpacto.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso,Long> {

    @Query("SELECT curso FROM Curso curso WHERE curso.nombre LIKE %:curso%")
    Curso findAlumnosByCurso(@Param("curso")String curso);
}
