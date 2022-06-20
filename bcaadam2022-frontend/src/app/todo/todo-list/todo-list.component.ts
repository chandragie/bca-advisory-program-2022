import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { throwError } from 'rxjs';
import { TodoService } from 'src/app/services/todo.service';

@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styleUrls: ['./todo-list.component.css'],
})
export class TodoListComponent implements OnInit {
  constructor(
    private todoService: TodoService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService
  ) {}

  todos: any;

  ngOnInit(): void {
    this.getTodos();

    this.activatedRoute.queryParams.subscribe((params) => {
      if (params['save'] !== undefined && params['save'] === 'true') {
        this.toastr.success('Todo saved succesfully!');
      }
    });
  }

  getTodos = () => {
    this.todoService.getAll().subscribe(
      (data) => {
        this.todos = data;
      },
      (error) => {
        this.toastr.error('Failed to get todo list');
        throw error;
      }
    );
  };

  updateTodo = (id: string) => {
    this.todoService.update(id).subscribe(
      () => {
        this.toastr.success('Todo updated!');
        this.getTodos();
      },
      (error) => {
        this.toastr.error('Failed to update todo ☹');
        throw error;
      }
    );
  };
}
