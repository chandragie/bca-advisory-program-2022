import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
})
export class SignupComponent implements OnInit {
  signupForm!: FormGroup;
  formObject: any;
  isSignupError: boolean = false;
  constructor(
    private authService: AuthService,
    private toastr: ToastrService,
    private router: Router
  ) {
    this.formObject = {
      name: '',
      username: '',
      password: '',
    };
  }

  ngOnInit(): void {
    this.isSignupError = false;
    this.signupForm = new FormGroup({
      name: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(30),
      ]),
      username: new FormControl('', [
        Validators.required,
        Validators.minLength(4),
        Validators.maxLength(10),
      ]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(6),
      ]),
    });
  }

  signup() {
    if (
      this.signupForm.get('name')?.invalid ||
      this.signupForm.get('username')?.invalid ||
      this.signupForm.get('password')?.invalid
    ) {
      this.isSignupError = true;
      this.toastr.error('All fields are mandatory!');
      return;
    }

    if (this.signupForm.invalid) {
      this.isSignupError = true;
      this.toastr.error('Login failed! Please check error message!');
      return;
    }

    this.formObject.name = this.signupForm.get('name')?.value;
    this.formObject.username = this.signupForm.get('username')?.value;
    this.formObject.password = this.signupForm.get('password')?.value;

    this.authService.signup(this.formObject).subscribe({
      next: () => {
        this.router.navigate(['/signin'], { queryParams: { signup: true } });
      },
      error: (err) => {
        if (err.status == 409) {
          this.toastr.error('Username already taken!');
        } else {
          this.toastr.error(err.message);
        }
      },
    });
  }
}
