package com.schooldevops.practical.simpleboard.repository;

import com.schooldevops.practical.simpleboard.entity.TodoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodoRepository extends CrudRepository<TodoEntity, Long> {

    List<TodoEntity> findAll();
}
