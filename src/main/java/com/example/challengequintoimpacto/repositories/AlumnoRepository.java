package com.example.challengequintoimpacto.repositories;

import com.example.challengequintoimpacto.entities.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    @Query("SELECT alumno FROM Alumno alumno WHERE alumno.nombre LIKE %:nombre%")
    List<Alumno> findAlumnoByNombre(@Param("nombre") String nombre);

    @Query("SELECT alumnos FROM Curso curso INNER JOIN curso.alumnos alumnos WHERE curso.nombre LIKE %:curso%")
    List<Alumno> findAlumnoByCurso(@Param("curso") String curso);

    @Query(value = "SELECT a.* FROM alumnos a " +
            "INNER JOIN curso_permiso cp ON cp.fk_alumno=a.id " +
            "INNER JOIN cursos c ON cp.fk_curso=c.id " +
            "WHERE a.nombre LIKE %:nombreAlumno% AND c.nombre LIKE  %:nombreCurso% " +
            "GROUP BY a.nombre", nativeQuery = true)
    List<Alumno> findAlumnosByNombreAnAndCursos(@Param("nombreCurso") String curso, @Param("nombreAlumno") String nombreAlumno);
}
