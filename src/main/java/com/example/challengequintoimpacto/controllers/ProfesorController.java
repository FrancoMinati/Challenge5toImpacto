package com.example.challengequintoimpacto.controllers;

import com.example.challengequintoimpacto.entities.Profesor;
import com.example.challengequintoimpacto.services.CursoService;
import com.example.challengequintoimpacto.services.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.challengequintoimpacto.constants.Constants.*;

@Controller
@RequestMapping("/profesores")
public class ProfesorController {

    @Autowired
    ProfesorService profesorService;
    @Autowired
    CursoService cursoService;

    @GetMapping("/abm")
    public String getABMprofesores(Model model){
        try{
            List<Profesor> profesors=profesorService.getAll();
            model.addAttribute(LISTA_DE_PROFESORES,profesors);
            return VIEW_ABM_PROFESORES;
        }catch (Exception ex){
            model.addAttribute(VIEW_ERROR,ex.getMessage());
            return VIEW_ERROR;
        }
    }
    @GetMapping("/formulario/{id}")
    public String getFormularioProfesor(Model model, @PathVariable("id")Long id){
        try{

            if (id == 0) {
                model.addAttribute("profesor", new Profesor());
            } else {
                model.addAttribute("profesor", profesorService.getOne(id));
            }
            if(!cursoService.getAll().isEmpty()){
                model.addAttribute(LISTA_DE_CURSOS,cursoService.getAll());
            }
            return "formulario_profesor";
        }catch (Exception ex){
            model.addAttribute(VIEW_ERROR,ex.getMessage());
            return VIEW_ERROR;
        }
    }
    @PostMapping("formulario/{id}")
    public String postFormularioProfesor(Model model,
                                      @RequestParam("id") Long id,
                                      @RequestParam("nombre")String nombre,
                                      @RequestParam("apellido")String apellido){
        try {
            if(id==0){
                profesorService.createProfesor(nombre,apellido);
            }else{
                profesorService.updateProfesor(nombre, apellido,id);
            }

            return "redirect:/profesores/abm";
        }catch (Exception ex){
            model.addAttribute(VIEW_ERROR,ex.getMessage());
            return VIEW_ERROR;
        }
    }
    @PostMapping("/eliminar/{id}")
    public String eliminarProfesor(Model model,
                                @PathVariable("id")Long id){
        try{
            profesorService.deleteOne(id);
            return "redirect:/profesores/abm";
        }catch (Exception ex){
            model.addAttribute(VIEW_ERROR,ex.getMessage());
            return VIEW_ERROR;
        }
    }

}
