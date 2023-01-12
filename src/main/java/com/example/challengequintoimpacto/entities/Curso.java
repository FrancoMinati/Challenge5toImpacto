package com.example.challengequintoimpacto.entities;

import com.example.challengequintoimpacto.enums.Turno;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "cursos")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @OneToOne
    private Profesor profesor;
    private Turno turno;
    private Date horario;
    @ManyToMany()
    private ArrayList<Alumno> alumnos;

}
