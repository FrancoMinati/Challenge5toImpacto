package com.example.challengequintoimpacto.entities;

import com.example.challengequintoimpacto.enums.Turno;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @ManyToOne()
    @JoinColumn(name = "fk_profesor")
    private Profesor profesor;

    @Enumerated(EnumType.STRING)
    @Column(name = "turno",nullable = false)
    private Turno turno;
    private String horario;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name="curso_permiso",
            joinColumns=@JoinColumn(name="fk_curso"),
            inverseJoinColumns=@JoinColumn(name="fk_alumno")
    )
    private List<Alumno> alumnos;

}
