package com.example.challengequintoimpacto.repositories;

import com.example.challengequintoimpacto.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso,Long> {

    @Query("SELECT curso FROM Curso curso WHERE curso.nombre LIKE %:curso%")
    Curso findCursoByNombre(@Param("curso")String curso);


}
