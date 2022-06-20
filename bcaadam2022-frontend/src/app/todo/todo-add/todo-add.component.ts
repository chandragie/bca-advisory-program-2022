import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { TodoService } from 'src/app/services/todo.service';

@Component({
  selector: 'app-todo-add',
  templateUrl: './todo-add.component.html',
  styleUrls: ['./todo-add.component.css'],
})
export class TodoAddComponent implements OnInit {
  addForm!: FormGroup;
  form: any;
  constructor(
    private todoService: TodoService,
    private router: Router,
    private toastr: ToastrService
  ) {
    this.form = {
      title: '',
    };
  }

  ngOnInit(): void {
    this.addForm = new FormGroup({
      title: new FormControl('', Validators.required),
    });
  }

  addTodo() {
    if (this.addForm.invalid) {
      this.toastr.error('Name what you want to do dude!');
      return;
    }

    this.form.title = this.addForm.get('title')?.value;
    this.todoService.add(this.form).subscribe(
      () => {
        this.router.navigate(['/'], { queryParams: { save: 'true' } });
      },
      () => {
        this.toastr.error('Failed to save todo! Please try again');
      }
    );
  }
}
