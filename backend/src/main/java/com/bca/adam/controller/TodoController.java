package com.bca.adam.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bca.adam.model.Todo;
import com.bca.adam.service.LoginService;
import com.bca.adam.service.TodoService;
import com.bca.adam.util.JWTTokenizer;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/todo")
@Slf4j
@Api(tags = "Todo")
public class TodoController {

    private final String AUTH_HEADER = "Authorization";

    @Autowired
    TodoService todoService;

    @Autowired
    LoginService loginService;

    @GetMapping("")
    public ResponseEntity<List<Todo>> getAll(HttpServletRequest req) {
        try {
            List<Todo> todos = todoService.getAll(
                    loginService.extractUserIdFromValidJWT(JWTTokenizer.validateJWT(req.getHeader(AUTH_HEADER))));
            if (todos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(todos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /*
     * basically you might want to use DTO object to store the request and response
     * payload instead of the model itself
     */
    @PostMapping("")
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo, HttpServletRequest req) {
        try {
            if (todo.getTitle() == null || !StringUtils.hasText(todo.getTitle())) {
                log.info("To add item to your todo list, you must give it a title!");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(
                    todoService.addTodo(todo,
                            loginService.extractUserIdFromValidJWT(
                                    JWTTokenizer.validateJWT(req.getHeader(AUTH_HEADER)))),
                    HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /*
     * here i give example that it is also possible receiving request payload using
     * Map
     */
    @PutMapping("")
    public ResponseEntity<Todo> modifyTodo(@RequestBody Map<String, String> body, HttpServletRequest req) {
        try {
            if (body.get("id") == null)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            return new ResponseEntity<>(todoService.modifyTodo(body.get("id"),
                    loginService.extractUserIdFromValidJWT(JWTTokenizer.validateJWT(req.getHeader(AUTH_HEADER)))),
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
