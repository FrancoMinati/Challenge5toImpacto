package com.example.challengequintoimpacto.services;

import com.example.challengequintoimpacto.entities.Curso;
import com.example.challengequintoimpacto.entities.Profesor;
import com.example.challengequintoimpacto.repositories.CursoRepository;
import com.example.challengequintoimpacto.repositories.ProfesorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ProfesorService implements BaseService<Profesor> {
    @Autowired
    ProfesorRepository profesorRepository;

    @Override
    @Transactional
    public List<Profesor> getAll() throws Exception {
        try{
            List<Profesor> profesores=this.profesorRepository.findAll();
            return profesores;
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public Profesor getOne(Long id) throws Exception {
        try{
            Optional<Profesor> opt=this.profesorRepository.findById(id);
            Profesor profesor=opt.get();
            return profesor;
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public Profesor saveOne(Profesor entity) throws Exception {
        try{
            Profesor profesor=this.profesorRepository.save(entity);
            return profesor;
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public Profesor updateOne(Profesor entity, Long id) throws Exception {
        try{
            Optional<Profesor> opt=this.profesorRepository.findById(id);
            if(opt.isPresent()){
                Profesor profesor=this.profesorRepository.save(entity);
                return profesor;
            }else{
                throw new Exception("No existe un profesor con el id: "+id);
            }
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public Boolean deleteOneSoft(Long id) throws Exception {
        try {
            Optional<Profesor> opt=this.profesorRepository.findById(id);
            if(opt.isPresent()){
                this.profesorRepository.deleteById(id);
                return true;
            }else{
                throw new Exception("No existe un profesor con el id: "+id);
            }
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
}
