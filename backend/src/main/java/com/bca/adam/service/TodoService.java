package com.bca.adam.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.bca.adam.model.Todo;
import com.bca.adam.repository.TodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TodoService {

    @Autowired
    TodoRepository todoRepo;

    public List<Todo> getAll(String userId) {
        List<Todo> todos = new ArrayList<>();
        todoRepo.findByCreatedByOrderByIsDoneAscCreatedDateDesc(userId).forEach(todos::add);

        if (todos.isEmpty()) {
            log.info("You have no todos!");
            return new ArrayList<>();
        }

        return todos;
    }

    public Todo addTodo(Todo todo, String createdBy) {
        return todoRepo.save(new Todo(todo.getTitle(), createdBy));
    }

    public Todo modifyTodo(String id, String modifiedBy) {
        Optional<Todo> todo = todoRepo.findById(UUID.fromString(id));
        if (todo.isPresent()) {
            Todo existTodo = todo.get();
            existTodo.setDone(!existTodo.isDone());
            existTodo.setModifiedDate(new Date());
            existTodo.setModifiedBy(modifiedBy);
            return todoRepo.save(existTodo);
        } else {
            throw new IllegalArgumentException(String.format("data with id %s not found.", id));
        }
    }

}
