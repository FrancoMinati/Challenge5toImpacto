package com.example.challengequintoimpacto.services;

import com.example.challengequintoimpacto.entities.Profesor;
import com.example.challengequintoimpacto.exceptions.CursoException;
import com.example.challengequintoimpacto.exceptions.ProfesorException;
import com.example.challengequintoimpacto.repositories.ProfesorRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfesorService implements BaseService<Profesor> {
    @Autowired
    ProfesorRepository profesorRepository;
    @Override
    @Transactional
    public List<Profesor> getAll() throws ProfesorException {
        try {
            return this.profesorRepository.findAll();
        } catch (Exception ex) {
            throw new ProfesorException(ex.getMessage());
        }
    }
    @Override
    @Transactional
    public Profesor getOne(Long id) throws ProfesorException {
        try {
            Optional<Profesor> opt = this.profesorRepository.findById(id);
            if (opt.isPresent()) {
                return opt.get();
            } else {
                throw new ProfesorException(ProfesorException.NOT_FOUND + id);
            }
        } catch (Exception ex) {
            throw new ProfesorException(ex.getMessage());
        }
    }
    @Override
    @Transactional
    public Profesor saveOne(Profesor entity) throws ProfesorException {
        try {
            return this.profesorRepository.save(entity);
        } catch (Exception ex) {
            throw new ProfesorException(ex.getMessage());
        }
    }
    @Override
    @Transactional
    public Profesor updateOne(Profesor entity, Long id) throws ProfesorException {
        try {
            Optional<Profesor> opt = this.profesorRepository.findById(id);
            if (opt.isPresent()) {
                return this.profesorRepository.save(entity);
            } else {
                throw new ProfesorException(ProfesorException.NOT_FOUND + id);
            }
        } catch (Exception ex) {
            throw new ProfesorException(ex.getMessage());
        }
    }
    @Override
    @Transactional
    public Boolean deleteOne(Long id) throws ProfesorException {
        try {
            Optional<Profesor> opt = this.profesorRepository.findById(id);
            if (opt.isPresent()) {
                this.profesorRepository.deleteById(id);
                return true;
            } else {
                throw new ProfesorException(ProfesorException.NOT_FOUND + id);
            }
        } catch (Exception ex) {
            throw new ProfesorException(ex.getMessage());
        }
    }

    public void createProfesor(String nombre, String apellido) throws ProfesorException{
        try {
            Profesor profesor = new Profesor();
            validarInformacion(nombre,apellido);
            profesor.setNombre(nombre);
            profesor.setApellido(apellido);
            profesor.setCursos(null);
            this.profesorRepository.save(profesor);
        } catch (Exception ex) {
            throw new ProfesorException(ex.getMessage());
        }
    }
    public void updateProfesor(String nombre, String apellido, Long id) throws ProfesorException {
        try {
            Profesor profesor = getOne(id);
            validarInformacion(nombre,apellido);
            profesor.setNombre(nombre);
            profesor.setApellido(apellido);
            this.profesorRepository.save(profesor);
        } catch (Exception ex) {
            throw new ProfesorException(ex.getMessage());
        }
    }
    @Transactional
    public void validarInformacion(String nombre, String apellido) throws CursoException {
        if(Strings.isBlank(nombre)){
            throw new CursoException("El Nombre no puede estar vacio");
        }
        if(Strings.isBlank(apellido)){
            throw new CursoException("El Apellido no puede estar vacio");
        }
    }
}
