package com.example.challengequintoimpacto.services;

import com.example.challengequintoimpacto.entities.Alumno;
import com.example.challengequintoimpacto.entities.Curso;
import com.example.challengequintoimpacto.repositories.AlumnoRepository;
import com.example.challengequintoimpacto.repositories.CursoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class AlumnoService implements BaseService<Alumno>{
    @Autowired
    AlumnoRepository  alumnoRepository;
    @Autowired
    CursoRepository cursoRepository;
    @Override
    @Transactional
    public List<Alumno> getAll() throws Exception {
        try{
            List<Alumno> alumnos=this.alumnoRepository.findAll();
            return alumnos;
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public Alumno getOne(Long id) throws Exception {
        try{
            Optional<Alumno> opt=this.alumnoRepository.findById(id);
            Alumno alumno=opt.get();
            return alumno;
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public Alumno saveOne(Alumno entity) throws Exception {
        try{
            Alumno alumno=this.alumnoRepository.save(entity);
            return alumno;
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public Alumno updateOne(Alumno entity, Long id) throws Exception {
        try{
            Optional<Alumno> opt=this.alumnoRepository.findById(id);
            if(opt.isPresent()){
               Alumno alumno=opt.get();
               this.alumnoRepository.save(alumno);
               return alumno;
            }else{
                throw new Exception("No existe un alumno con el id: "+id);
            }
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public Boolean deleteOneSoft(Long id) throws Exception {
       try {
           Optional<Alumno> opt=this.alumnoRepository.findById(id);
           if(opt.isPresent()){
               this.alumnoRepository.deleteById(id);
               return true;
           }else{
               throw new Exception("No existe un alumno con el id: "+id);
           }
       }catch (Exception ex){
           throw new Exception(ex.getMessage());
       }
    }
    @Transactional
    public Alumno getByNombre(String nombre) throws Exception{
        try{
            Alumno alumno=this.alumnoRepository.findAlumnoByNombre(nombre);
            return alumno;
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
    @Transactional
    public List<Alumno> getAlumnosByCurso(String nombre) throws Exception{
        try{
            Curso curso=this.cursoRepository.findAlumnosByCurso(nombre);
            List<Alumno> alumnos=curso.getAlumnos();
            return alumnos;
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
}
