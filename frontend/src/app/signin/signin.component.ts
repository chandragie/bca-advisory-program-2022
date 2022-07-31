import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../services/auth.service';
import { TokenService } from '../services/token.service';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css'],
})
export class SigninComponent implements OnInit {
  loginForm!: FormGroup;
  formObject: any;
  isLoginFailed: boolean = false;
  constructor(
    private authService: AuthService,
    private toastr: ToastrService,
    private token: TokenService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    this.formObject = {
      username: '',
      password: '',
    };
  }

  ngOnInit(): void {
    this.isLoginFailed = false;

    this.activatedRoute.queryParams.subscribe((params) => {
      if (params['signout'] !== undefined && params['signout'] === 'true') {
        this.toastr.success("You've logged out successfully!");
      } else if (
        params['signup'] !== undefined &&
        params['signup'] === 'true'
      ) {
        this.toastr.success('Sign up success! You now can login');
      }
    });

    this.loginForm = new FormGroup({
      username: new FormControl('', [
        Validators.required,
        Validators.minLength(4),
        Validators.maxLength(10),
      ]),
      password: new FormControl('', [Validators.required]),
    });
  }

  login() {
    if (
      this.loginForm.get('username')?.invalid ||
      this.loginForm.get('password')?.invalid
    ) {
      this.toastr.error('All fields are mandatory!');
      this.isLoginFailed = true;
      return;
    }

    if (this.loginForm.invalid) {
      this.toastr.error('Login failed! Please check error message!');
      this.isLoginFailed = true;
      return;
    }

    this.formObject.username = this.loginForm.get('username')?.value;
    this.formObject.password = this.loginForm.get('password')?.value;

    this.authService.signin(this.formObject).subscribe({
      next: (data) => {
        this.isLoginFailed = false;
        this.token.saveToken(data);
        this.router.navigate(['/']);
      },
      error: (err) => {
        console.log(err);

        this.toastr.error('Login failed!');
        this.isLoginFailed = true;
      },
    });
  }
}
