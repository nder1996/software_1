import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from '../shared/services/login/login.service';
import { AuthService } from '@core/service/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  formLogin: FormGroup;

  constructor(
    private readonly router: Router,
    private readonly formBuilder: FormBuilder,
    private readonly loginService: LoginService,
    private readonly auth: AuthService,
  ) { }

  ngOnInit(): void {
    this.construirFormulario();
    const token = localStorage.getItem('token');
    if (token) {
      console.log('Token encontrado, redirigiendo a home...');
      this.redirectUsers();
    }
  }

  private construirFormulario(): void {
    this.formLogin = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  public async ingresar(): Promise<void> {
    if (this.formLogin.valid) {
      try {
        const response = await this.loginService.login(this.formLogin.value.email, this.formLogin.value.password);
        if (response.token) {
          localStorage.setItem('token', response.token);
          console.log('Login exitoso, redirigiendo a home...');
          this.redirectUsers();
        } else {
          console.error('❌ Error: No se pudo validar el token');
        }
      } catch (error) {
        console.error('❌ Error durante el login:', error);
      }
    } else {
      console.error('❌ Error: Formulario inválido');
      Object.values(this.formLogin.controls).forEach(control => {
        if (control.invalid) {
          control.markAsTouched();
        }
      });
    }
  }

  /**
   * Este método no se puede modificar
   * */
  public redirectUsers(): void {
    this.router.navigateByUrl('/users/list');
  }

}
