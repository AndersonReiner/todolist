package com.andersonreiner.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andersonreiner.todolist.utils.Utils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController{

    @Autowired
    private ITaskRepository itaskrepository;
    
    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
        System.out.println("Chegou no controller");
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID)idUser);

        var currentDate = LocalDateTime.now();
        // 10/11/2023
        // 10/10/2023
        if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio / termino deve ser mais que a data atual!!");
        }
        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio deve ser menor que a data de termino!!");
        }

        var task = this.itaskrepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        var tasks = this.itaskrepository.findByIdUser((UUID)idUser);
        return tasks;
    }
    
    //http://localhost:8080/tasks/54567898-hgfdgiu-8765678 varial do path.
    @PutMapping("/{id}")
    public TaskModel update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id){
        var idUser = request.getAttribute("idUser");
        
        var task = this.itaskrepository.findById(id).orElse(null);
        Utils.copyNonNullProperties(taskModel, task);
        
        // taskModel.setIdUser((UUID)idUser);
        // taskModel.setId(id);
        return this.itaskrepository.save(task);
    }

    
}
