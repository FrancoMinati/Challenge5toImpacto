package com.example.challengequintoimpacto.repositories;

import com.example.challengequintoimpacto.entities.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AlumnoRepository extends JpaRepository<Alumno,Long> {

    @Query("SELECT alumno FROM Alumno alumno WHERE alumno.nombre LIKE %:nombre%")
    List<Alumno> findAlumnoByNombre(@Param("nombre")String nombre);

}
