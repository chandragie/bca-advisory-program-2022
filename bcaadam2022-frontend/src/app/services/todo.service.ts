import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class TodoService {
  constructor(private httpClient: HttpClient) {}

  getAll = (): Observable<any> => {
    return this.httpClient.get(`${environment.baseUrl}/todo`, {
      responseType: 'json',
    });
  };

  add = (body: any): Observable<any> => {
    return this.httpClient.post(`${environment.baseUrl}/todo`, { ...body });
  };

  update = (id: string): Observable<any> => {
    return this.httpClient.put(`${environment.baseUrl}/todo`, { id: id });
  };
}
