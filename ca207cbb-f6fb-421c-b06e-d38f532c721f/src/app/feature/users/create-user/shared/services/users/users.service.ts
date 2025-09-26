import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@environments/environment';
import { CreateUser } from '@feature/login/shared/model/request/create-user.model';
import { ResponseCreateUserModel } from '@feature/login/shared/model/response/create-user-response.model';

/**
 * El nombre de las clases o métodos no se pueden cambiar
 * */
@Injectable({
  providedIn: 'root',
})
export class UsersService {

  private TOKEN = "";
  private apiUsers = environment.API;

  constructor(private http: HttpClient) {
    this.TOKEN = "reqres-free-v1"
  }

  private getHeaders(): HttpHeaders {
    return new HttpHeaders({ 'x-api-key': this.TOKEN });
  }

  getUsers(): Promise<any> {
    return this.http.get<any>(`${this.apiUsers}/users?page=1&per_page=6`, { headers: this.getHeaders() }).toPromise();
  }

  getUsersByPage(page: number): Promise<any> {
    return this.http.get<any>(`${this.apiUsers}/users?page=${page}&per_page=6`, { headers: this.getHeaders() }).toPromise();
  }

  createUser(userNew: CreateUser): Promise<ResponseCreateUserModel> {
    return this.http.post<ResponseCreateUserModel>(`${this.apiUsers}/users`, userNew, { headers: this.getHeaders() }).toPromise();
  }

  deleteUserForIndex(userId: number): Promise<any> {
    return this.http.delete<any>(`${this.apiUsers}/users/${userId}`, { headers: this.getHeaders(), observe: 'response' }).toPromise();
  }
}
