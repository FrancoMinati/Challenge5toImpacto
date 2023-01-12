package com.example.challengequintoimpacto.services;

import com.example.challengequintoimpacto.entities.Alumno;
import com.example.challengequintoimpacto.entities.Curso;
import com.example.challengequintoimpacto.entities.Profesor;
import com.example.challengequintoimpacto.repositories.AlumnoRepository;
import com.example.challengequintoimpacto.repositories.CursoRepository;
import com.example.challengequintoimpacto.repositories.ProfesorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class CursoService implements BaseService<Curso>{
    @Autowired
    CursoRepository cursoRepository;
    @Override
    @Transactional
    public List<Curso> getAll() throws Exception {
        try{
            List<Curso> cursos=this.cursoRepository.findAll();
            return cursos;
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public Curso getOne(Long id) throws Exception {
        try{
            Optional<Curso> opt=this.cursoRepository.findById(id);
            Curso curso=opt.get();
            return curso;
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public Curso saveOne(Curso entity) throws Exception {
        try{
            Curso curso=this.cursoRepository.save(entity);
            return curso;
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public Curso updateOne(Curso entity, Long id) throws Exception {
        try{
            Optional<Curso> opt=this.cursoRepository.findById(id);
            if(opt.isPresent()){
                Curso curso=this.cursoRepository.save(entity);
                return curso;
            }else{
                throw new Exception("No existe un curso con el id: "+id);
            }
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public Boolean deleteOneSoft(Long id) throws Exception {
        try {
            Optional<Curso> opt=this.cursoRepository.findById(id);
            if(opt.isPresent()){
                this.cursoRepository.deleteById(id);
                return true;
            }else{
                throw new Exception("No existe un curso con el id: "+id);
            }
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
    @Transactional

    // Hay varias formas de interpretar esto
    /*
        1 Alternativa
    Se me ocurrio pasar el id del profesor y compararlo con el id del profesor asignado al curso
       si coinciden borrarlo, esto para permitir una busqueda mas profunda del profesor

       2 Alternativa
       La segunda alternativa que se me ocurrio era simplemente borrar al profesor del curso, que se
       eligiera, pero creo que no es lo que se buscaba con la consigna

     */

    public Curso removerProfesor(Long idProf, Long idCurso) throws Exception{
        try{
            Curso curso=getOne(idCurso);
            Long id=curso.getProfesor().getId();
            if(id.equals(idProf)){
                curso.setProfesor(null);
                curso=updateOne(curso,idCurso);
                return curso;
            }else{
                throw new Exception("No existe un profesor con el id: "+id);
            }
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }


}
