package com.bca.adam.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.bca.adam.model.Todo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, String> {
    Optional<Todo> findById(UUID id);

    List<Todo> findAll();
}
