package com.example.challengequintoimpacto.services;

import com.example.challengequintoimpacto.entities.Alumno;
import com.example.challengequintoimpacto.entities.Curso;
import com.example.challengequintoimpacto.exceptions.AlumnoException;
import com.example.challengequintoimpacto.exceptions.CursoException;
import com.example.challengequintoimpacto.repositories.AlumnoRepository;
import com.example.challengequintoimpacto.repositories.CursoRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class AlumnoService implements BaseService<Alumno> {
    @Autowired
    AlumnoRepository alumnoRepository;
    @Autowired
    CursoRepository cursoRepository;

    @Override
    @Transactional
    public List<Alumno> getAll() throws AlumnoException {
        try {
            return this.alumnoRepository.findAll();
        } catch (Exception ex) {
            throw new AlumnoException(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public Alumno getOne(Long id) throws AlumnoException {
        try {
            Optional<Alumno> opt = this.alumnoRepository.findById(id);
            if (opt.isPresent()) {
                return opt.get();
            } else {
                throw new AlumnoException("No se encuentra un alumno con ese id: " + id);
            }
        } catch (Exception ex) {
            throw new AlumnoException(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public Alumno saveOne(Alumno entity) throws AlumnoException {
        try {
            return this.alumnoRepository.save(entity);
        } catch (Exception ex) {
            throw new AlumnoException(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public Alumno updateOne(Alumno entity, Long id) throws AlumnoException {
        try {
            Optional<Alumno> opt = this.alumnoRepository.findById(id);
            if (opt.isPresent()) {
                return this.alumnoRepository.save(opt.get());
            } else {
                throw new AlumnoException("No existe un alumno con el id: " + id);
            }
        } catch (Exception ex) {
            throw new AlumnoException(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public Boolean deleteOne(Long id) throws AlumnoException {
        try {
            Optional<Alumno> opt = this.alumnoRepository.findById(id);
            if (opt.isPresent()) {
                this.alumnoRepository.deleteById(id);
                return true;
            } else {
                throw new AlumnoException("No existe un alumno con el id: " + id);
            }
        } catch (Exception ex) {
            throw new AlumnoException(ex.getMessage());
        }
    }

    @Transactional
    public List<Alumno> getByNombre(String nombre) throws AlumnoException {
        try {
            return this.alumnoRepository.findAlumnoByNombre(nombre);
        } catch (Exception ex) {
            throw new AlumnoException(ex.getMessage());
        }
    }

    @Transactional
    public List<Alumno> getAlumnosByCurso(String nombre) throws AlumnoException {
        try {

            return this.alumnoRepository.findAlumnoByCurso(nombre);
        } catch (Exception ex) {
            throw new AlumnoException(ex.getMessage());
        }
    }
    @Transactional
    public List<Alumno> getAlumnosByNombreAndCurso(String nombreAlumno,String nombreCurso) throws AlumnoException {
        try {
            return this.alumnoRepository.findAlumnosByNombreAnAndCursos(nombreCurso,nombreAlumno);
        } catch (Exception ex) {
            throw new AlumnoException(ex.getMessage());
        }
    }
    @Transactional
    public void crearAlumno(String nombre, String historia, Date fechaNacimiento, int edad, List<Curso> cursos) throws AlumnoException {
        try {
            Alumno alumno = new Alumno();
            validarInformacion(nombre,historia,fechaNacimiento);
            alumno.setNombre(nombre);
            alumno.setEdad(edad);
            alumno.setHistoria(historia);
            alumno.setFechaNacimiento(fechaNacimiento);
            alumno.setCursos(cursos);
            this.alumnoRepository.save(alumno);
        } catch (Exception ex) {
            throw new AlumnoException(ex.getMessage());
        }
    }

    @Transactional
    public void updateAlumno(String nombre, String historia, Date fechaNacimiento, int edad, Long id) throws AlumnoException {
        try {
            Alumno alumno = getOne(id);
            validarInformacion(nombre,historia,fechaNacimiento);
            alumno.setNombre(nombre);
            alumno.setEdad(edad);
            alumno.setHistoria(historia);
            alumno.setFechaNacimiento(fechaNacimiento);
            this.alumnoRepository.save(alumno);
        } catch (Exception ex) {
            throw new AlumnoException(ex.getMessage());
        }

    }
    public void validarInformacion(String nombre, String historia,Date fechaNacimiento) throws CursoException {
        if(Strings.isBlank(nombre)){
            throw new CursoException("El Nombre no puede estar vacío");
        }
        if(Strings.isBlank(historia)){
            throw new CursoException("La Historia no puede estar vacía");
        }
        if(fechaNacimiento==null){
            throw new CursoException("La Fecha de Nacimiento no puede estar vacía");
        }
    }
    public List<Alumno> filtrarListaPorNombreYCurso(String nombre, String curso) throws AlumnoException {
        List<Alumno> filtrados=new ArrayList<>();
        if(nombre!=null && curso!=null){
            filtrados=getAlumnosByNombreAndCurso(nombre,curso);
        }else{
            if(nombre!=null){
                filtrados=getByNombre(nombre);
            }
            if(curso!=null){
                filtrados=getAlumnosByCurso(curso);
            }
        }
        return filtrados;
    }
}
