import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/services/auth.service';
import { TokenService } from 'src/app/services/token.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  isLoggedIn: boolean = false;
  constructor(
    private token: TokenService,
    private auth: AuthService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.isLoggedIn = this.token.isLoggedIn();
  }

  signout() {
    this.auth.signout().subscribe({
      next: () => {
        this.token.removeToken();
        this.router.navigate(['/signin'], { queryParams: { signout: 'true' } });
      },
      error: (err) => {
        console.log(err);
        this.toastr.error(JSON.parse(err.error).message);
        this.token.removeToken();
      },
    });
  }
}
