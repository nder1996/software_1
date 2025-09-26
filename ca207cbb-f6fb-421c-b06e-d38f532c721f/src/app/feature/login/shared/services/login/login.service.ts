import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, from } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from '@environments/environment';
import { AuthService } from '@core/service/auth.service';
import { NotificacionService } from '@core/service/notificacion.service';

@Injectable({
  providedIn: 'root',
})
export class LoginService {

  private apiLogin = environment.API;

  constructor(private http: HttpClient, private auth: AuthService
  ) { }

  private createHeaders(): HttpHeaders {
    return new HttpHeaders({
      'x-api-key': 'reqres-free-v1',
      'Content-Type': 'application/json'
    });
  }

  private handleLoginResponse(data: any): void {
    if (data.token) {
      this.auth.setToken(data.token);
    }
  }

  /**
  * El nombre de este metodo no debería ser cambiado, pero de ser necesario podrías cambiar la firma
   * */
  public login(email: string, password: string): Promise<{ token: string }> {
    const url = `${this.apiLogin}/login`;
    const headers = this.createHeaders();
    const body = {
      email: email,
      password: password
    };

    return this.http.post<{ token: string }>(url, body, { headers }).toPromise()
      .then(data => {
        this.handleLoginResponse(data);
        return data;
      })
      .catch(error => {
        console.error('❌ LOGIN - Error:', error);
        throw error;
      });
  }

}