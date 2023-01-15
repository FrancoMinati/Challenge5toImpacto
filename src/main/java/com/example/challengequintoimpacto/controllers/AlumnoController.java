package com.example.challengequintoimpacto.controllers;

import com.example.challengequintoimpacto.entities.Alumno;
import com.example.challengequintoimpacto.entities.Curso;
import com.example.challengequintoimpacto.services.AlumnoService;
import com.example.challengequintoimpacto.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.challengequintoimpacto.constants.Constants.*;


@Controller
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    AlumnoService alumnoService;
    @Autowired
    CursoService cursoService;


    @GetMapping("/abm")
    public String getAbmAlumnos(Model model){
        try{
            List<Alumno> alumnos=alumnoService.getAll();
            model.addAttribute("alumnos",alumnos);
            if(!cursoService.getAll().isEmpty()){
                model.addAttribute(LISTA_DE_CURSOS,cursoService.getAll());
            }
            return VIEW_ABM_ALUMNOS;
        }catch (Exception ex){
            model.addAttribute(VIEW_ERROR,ex.getMessage());
            return VIEW_ERROR;
        }
    }
    @GetMapping("abm/filtro")
    public String filtrarPorNombre(Model model,
                                   @RequestParam(value = "nombre",required = false)String nombre,
                                   @RequestParam(value = "curso",required = false)String curso
                                   ){
        try{
            if(!cursoService.getAll().isEmpty()){
                model.addAttribute(LISTA_DE_CURSOS,cursoService.getAll());
            }
            model.addAttribute(LISTA_DE_ALUMNOS,alumnoService.filtrarListaPorNombreYCurso(nombre,curso));
            if(!curso.equals("")){
                model.addAttribute("filtro",curso);
            }
            model.addAttribute("filtroAlumno",nombre);
            return VIEW_ABM_ALUMNOS;
        }catch (Exception ex){
            model.addAttribute(VIEW_ERROR,ex.getMessage());
            return VIEW_ERROR;
        }
    }

    @GetMapping("/formulario/{id}")
    public String getFormularioProfesor(Model model, @PathVariable("id")Long id){
        try{
            if(cursoService.getAll().isEmpty()){
                model.addAttribute(VIEW_ERROR,"No es posible agregar alumnos, ya que no hay cursos disponibles");
                return VIEW_ABM_ALUMNOS;
            }else{
                model.addAttribute(LISTA_DE_CURSOS,cursoService.getAll());
            }
            if (id == 0) {
                model.addAttribute("alumno", new Alumno());
                model.addAttribute("creado",0);
            } else {
                model.addAttribute("alumno", alumnoService.getOne(id));
            }
            return "formulario_alumno";
        }catch (Exception ex){
            model.addAttribute(VIEW_ERROR,ex.getMessage());
            return VIEW_ERROR;
        }
    }
    @PostMapping("formulario/{id}")
    public String postFormularioProfesor(Model model,
                                         @RequestParam("id") Long id,
                                         @RequestParam("nombre")String nombre,
                                         @RequestParam("historia")String historia,
                                         @RequestParam("fecha_nacimiento")
                                             @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento,
                                         @RequestParam("edad")int edad,
                                         @RequestParam(required = false,value = "listaDeCursos")List<Long> listaDeCursos){
        try {

            if (id == 0) {
                List<Curso> cursos=new ArrayList<>();
                if(listaDeCursos!=null){
                    for(Long idC : listaDeCursos){
                        cursos.add(cursoService.getOne(idC));
                    }
                }
                alumnoService.crearAlumno(nombre,historia,fechaNacimiento,edad,cursos);
            } else {
                alumnoService.updateAlumno(nombre,historia,fechaNacimiento,edad,id);
            }
            return "redirect:/alumnos/abm";
        }catch (Exception ex){
            model.addAttribute(VIEW_ERROR,ex.getMessage());
            return VIEW_ERROR;
        }
    }
    @PostMapping("/eliminar/{id}")
    public String eliminarCurso(Model model,
                                @PathVariable("id")Long id){
        try{
            alumnoService.deleteOne(id);
            return "redirect:/alumnos/abm";
        }catch (Exception ex){
            model.addAttribute(VIEW_ERROR,ex.getMessage());
            return VIEW_ERROR;
        }
    }

}
