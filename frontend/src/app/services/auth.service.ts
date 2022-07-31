import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  signup = (body: any): Observable<any> => {
    return this.http.post(
      `${environment.baseUrl}/user`,
      {
        ...body,
      },
      { responseType: 'json' }
    );
  };

  signin = (body: any): Observable<any> => {
    return this.http.post(
      `${environment.baseUrl}/sign/in`,
      {
        ...body,
      },
      { responseType: 'text' }
    );
  };

  signout() {
    return this.http.post(`${environment.baseUrl}/sign/out`, null, {
      responseType: 'text',
    });
  }
}
