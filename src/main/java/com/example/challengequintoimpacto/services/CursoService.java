package com.example.challengequintoimpacto.services;

import com.example.challengequintoimpacto.entities.Alumno;
import com.example.challengequintoimpacto.entities.Curso;
import com.example.challengequintoimpacto.entities.Profesor;
import com.example.challengequintoimpacto.enums.Turno;
import com.example.challengequintoimpacto.exceptions.CursoException;
import com.example.challengequintoimpacto.repositories.CursoRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService implements BaseService<Curso>{
    @Autowired
    CursoRepository cursoRepository;
    @Override
    @Transactional
    public List<Curso> getAll() throws CursoException {
        try{
            return this.cursoRepository.findAll();
        }catch (Exception ex){
            throw new CursoException(ex.getMessage());
        }
    }
    @Override
    @Transactional
    public Curso getOne(Long id) throws CursoException {
        try{
            Optional<Curso> opt=this.cursoRepository.findById(id);
            if(opt.isPresent()){
                return opt.get();
            }else{
                throw new CursoException("No existe un curso con ese id: "+id);
            }
        }catch (Exception ex){
            throw new CursoException(ex.getMessage());
        }
    }
    @Override
    @Transactional
    public Curso saveOne(Curso entity) throws CursoException {
        try{
            return this.cursoRepository.save(entity);
        }catch (Exception ex){
            throw new CursoException(ex.getMessage());
        }
    }
    @Override
    @Transactional
    public Curso updateOne(Curso entity, Long id) throws CursoException {
        try{
            Optional<Curso> opt=this.cursoRepository.findById(id);
            if(opt.isPresent()){
                return this.cursoRepository.save(entity);
            }else{
                throw new CursoException("No existe un curso con el id: "+id);
            }
        }catch (Exception ex){
            throw new CursoException(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public Boolean deleteOne(Long id) throws CursoException {
        try {
            Optional<Curso> opt=this.cursoRepository.findById(id);
            if(opt.isPresent()){
                this.cursoRepository.deleteById(id);
                return true;
            }else{
                throw new CursoException("No existe un curso con el id: "+id);
            }
        }catch (Exception ex){
            throw new CursoException(ex.getMessage());
        }
    }

    // Hay varias formas de interpretar esto
    /*
        1 Alternativa
    Se me ocurrio pasar el id del profesor y compararlo con el id del profesor asignado al curso
       si coinciden borrarlo, esto para permitir una busqueda mas profunda del profesor

       2 Alternativa
       La segunda alternativa que se me ocurrio era simplemente borrar al profesor del curso, que se
       eligiera, pero creo que no es lo que se buscaba con la consigna

     */
    @Transactional
    public void crearCurso(String nombre, Turno turno, String horario,Profesor profesor,List<Alumno> alumnos) throws CursoException{
        try{
            Curso curso=new Curso();
            validarInformacion(nombre,horario);
            curso.setNombre(nombre);
            curso.setProfesor(profesor);
            curso.setTurno(turno);
            curso.setHorario(horario);
            curso.setAlumnos(alumnos);
            this.cursoRepository.save(curso);
        }catch (Exception ex){
            throw new CursoException(ex.getMessage());
        }
    }
    @Transactional
    public void updateCurso(String nombre, Turno turno, String horario,Profesor profesor,Long id,List<Alumno> alumnos) throws CursoException{
        try{
            Curso curso=getOne(id);
            validarInformacion(nombre,horario);
            curso.setNombre(nombre);
            curso.setProfesor(profesor);
            curso.setTurno(turno);
            curso.setHorario(horario);
            curso.setAlumnos(alumnos);
            this.cursoRepository.save(curso);
        }catch (Exception ex){
            throw new CursoException(ex.getMessage());
        }
       
    }
    @Transactional
    public void validarInformacion(String nombre, String horario) throws CursoException{
        if(Strings.isBlank(nombre)){
            throw new CursoException("El Nombre no puede estar vacio");
        }
        if(Strings.isBlank(horario)){
            throw new CursoException("El Horario no puede estar vacio");
        }
    }
    @Transactional
    public Curso removerProfesor(Long idProf, Long idCurso) throws CursoException{
        try{
            Curso curso=getOne(idCurso);
            Long id=curso.getProfesor().getId();
            if(id.equals(idProf)){
                curso.setProfesor(null);
                curso=updateOne(curso,idCurso);
                return curso;
            }else{
                throw new CursoException("No existe un profesor con el id: "+id);
            }
        }catch (Exception ex){
            throw new CursoException(ex.getMessage());
        }
    }
    @Transactional
    public List<Alumno> filtrarAlumnosNoCursantes(Long id,List<Alumno> alumnos) throws CursoException{
        try{
            Curso curso=getOne(id);
            if(curso.getAlumnos().isEmpty()){
                return alumnos;
            }
            List<Alumno> alumnosQueNoCursanLaMateria=alumnos;
            alumnosQueNoCursanLaMateria.removeAll(curso.getAlumnos());

            return alumnosQueNoCursanLaMateria;
        }catch (Exception ex){
            throw new CursoException(ex.getMessage());
        }
    }

}
