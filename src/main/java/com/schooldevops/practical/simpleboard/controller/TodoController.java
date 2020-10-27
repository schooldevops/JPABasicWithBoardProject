package com.schooldevops.practical.simpleboard.controller;

import com.schooldevops.practical.simpleboard.entity.TodoEntity;
import com.schooldevops.practical.simpleboard.repository.TodoRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequestMapping("/todos")
@RestController
public class TodoController {

    final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping(path = "")
    public List<TodoEntity> getAllTodos() {
        return todoRepository.findAll();
    }

    @PostMapping
    public TodoEntity postTodo(@RequestBody TodoEntity todo) {
        if (todo.getCreatedAt() == null) {
            todo.setCreatedAt(LocalDateTime.now());
        }
        return todoRepository.save(todo);
    }

    @PutMapping(path = "/{id}")
    public TodoEntity putTodo(@PathVariable("id") Long id, @RequestBody TodoEntity todo) {
        Optional<TodoEntity> todoResult = todoRepository.findById(id);
        TodoEntity todoEntity = todoResult.orElseThrow(() -> new RuntimeException("There are any result by " + id));

        if (todo.getTodo() != null) {
            todoEntity.setTodo(todo.getTodo());
        }

        if (todo.getDone() != null) {
            todoEntity.setDone(todo.getDone());
        }

        return todoRepository.save(todoEntity);
    }

    @DeleteMapping(path = "/{id}")
    public TodoEntity deleteTodo(@PathVariable("id") Long id) {
        Optional<TodoEntity> todoResult = todoRepository.findById(id);
        TodoEntity todoEntity = todoResult.orElseThrow(() -> new RuntimeException("There are any result by " + id));

        todoRepository.delete(todoEntity);

        return todoEntity;
    }
}
