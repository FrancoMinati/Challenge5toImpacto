package com.example.challengequintoimpacto.repositories;
import com.example.challengequintoimpacto.entities.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor,Long> {
}
