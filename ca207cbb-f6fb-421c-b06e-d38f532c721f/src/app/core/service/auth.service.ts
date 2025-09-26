import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() {}

  isAuthenticated(): boolean {
    const token = localStorage.getItem("token")
    return !!token;
  }

  setToken(token: string): void {
    localStorage.setItem("token", token);
  }
}
