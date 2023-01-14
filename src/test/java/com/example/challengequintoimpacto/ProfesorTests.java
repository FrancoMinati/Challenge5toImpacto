package com.example.challengequintoimpacto;

import com.example.challengequintoimpacto.entities.Profesor;
import com.example.challengequintoimpacto.repositories.ProfesorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProfesorTests {
    @Autowired
    private ProfesorRepository profesorRepository;

    @Test
     void getProfesorTest(){
        Profesor profesor=new Profesor();
        profesor.setNombre("Franco");
        profesor.setApellido("Minati");
        this.profesorRepository.save(profesor);
        assertNotNull(this.profesorRepository.findById(1L));
    }
}
