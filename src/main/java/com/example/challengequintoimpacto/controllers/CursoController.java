package com.example.challengequintoimpacto.controllers;

import com.example.challengequintoimpacto.entities.Alumno;
import com.example.challengequintoimpacto.entities.Curso;
import com.example.challengequintoimpacto.enums.Turno;
import com.example.challengequintoimpacto.services.AlumnoService;
import com.example.challengequintoimpacto.services.CursoService;
import com.example.challengequintoimpacto.services.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.challengequintoimpacto.constants.Constants.*;

@Controller
@RequestMapping("/cursos")
public class CursoController {
    @Autowired
    CursoService cursoService;
    @Autowired
    ProfesorService profesorService;
    @Autowired
    AlumnoService alumnoService;

    static final String REDIRECT_ABM = "redirect:/cursos/abm";

    @GetMapping("/abm")
    public String abmCursos(Model model) {
        try {
            List<Curso> cursos = cursoService.getAll();
            model.addAttribute(LISTA_DE_CURSOS, cursos);
            return VIEW_ABM_CURSOS;
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return VIEW_ERROR;
        }

    }

    @GetMapping("/formulario/{id}")
    public String getFormularioCurso(Model model, @PathVariable("id") Long id) {
        try {
            model.addAttribute("turnos", Turno.values());
            if (id == 0) {
                model.addAttribute("curso", new Curso());
                if (!alumnoService.getAll().isEmpty()) {
                    model.addAttribute(LISTA_DE_ALUMNOS, alumnoService.getAll());
                }
            } else {
                model.addAttribute("curso", cursoService.getOne(id));
                List<Alumno> alumnosQueNoCursan = cursoService.filtrarAlumnosNoCursantes(id, alumnoService.getAll());
                model.addAttribute("alumnosQueNoCursan", alumnosQueNoCursan);
            }
            if (profesorService.getAll().isEmpty()) {
                model.addAttribute(VIEW_ERROR, "No es posible agregar cursos, ya que no hay profesores disponibles");
                return VIEW_ABM_CURSOS;
            } else {
                model.addAttribute(LISTA_DE_PROFESORES, profesorService.getAll());
            }
            return "formulario_curso";
        } catch (Exception ex) {
            model.addAttribute(VIEW_ERROR, ex.getMessage());
            return VIEW_ERROR;
        }
    }

    @PostMapping("formulario/{id}")
    public String postFormularioCurso(Model model,
                                      @RequestParam("id") Long id,
                                      @RequestParam("nombreCurso") String nombreCurso,
                                      @RequestParam("turno") Turno turno,
                                      @RequestParam("horario") String horario,
                                      @RequestParam(required = false, name = "profesor") Long profesorId,
                                      @RequestParam(value = "alumnos", required = false) List<Long> alumnos) {
        try {

            List<Alumno> lista = new ArrayList<>();
            if(alumnos!=null){
                for (Long idC : alumnos) {
                    lista.add(alumnoService.getOne(idC));
                }
            }
            if (id == 0) {
                cursoService.crearCurso(nombreCurso, turno, horario, profesorService.getOne(profesorId), lista);
            } else {

                cursoService.updateCurso(nombreCurso, turno, horario, profesorService.getOne(profesorId), id, lista);
            }

            return REDIRECT_ABM;
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return VIEW_ERROR;
        }
    }

    @PostMapping("/remover_profesor")
    public String removerProfesor(Model model,
                                  @RequestParam("id") Long id,
                                  @RequestParam("idProf") Long idProf) {
        try {
            cursoService.removerProfesor(idProf, id);
            return REDIRECT_ABM;
        } catch (Exception ex) {
            model.addAttribute(VIEW_ERROR, ex.getMessage());
            return VIEW_ERROR;
        }

    }

    @PostMapping("/eliminar/{id}")
    public String eliminarCurso(Model model,
                                @PathVariable("id") Long id) {
        try {
            cursoService.deleteOne(id);
            return REDIRECT_ABM;
        } catch (Exception ex) {
            model.addAttribute(VIEW_ERROR, ex.getMessage());
            return VIEW_ERROR;
        }
    }

}
