import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IncludeHeaderComponent } from './components/wrapper/include-header/include-header.component';
import { SigninComponent } from './signin/signin.component';
import { SignupComponent } from './signup/signup.component';
import { TodoAddComponent } from './todo/todo-add/todo-add.component';
import { TodoListComponent } from './todo/todo-list/todo-list.component';
import { AuthGuard } from './util/AuthGuard';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'todos' },
  {
    path: '',
    component: IncludeHeaderComponent,
    children: [
      { path: 'todos', component: TodoListComponent , canActivate:[AuthGuard] },
      { path: 'add', component: TodoAddComponent , canActivate:[AuthGuard]},
    ],
  },
  { path: 'signin', component: SigninComponent, children: [] },
  { path: 'signup', component: SignupComponent, children: [] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
