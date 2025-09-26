import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss'],
})
export class NavBarComponent implements OnInit {

  public isResponsive: boolean = false;
  public currentRoute: string = '';


  constructor(
    private readonly router: Router,
  ) {
  }


  toggleNavbar(): void {
    this.isResponsive = !this.isResponsive;
  }

  ngOnInit(): void {
  }

  /**
   * Este método no se puede modificar
   * */
  public logout(): void {
    this.router.navigateByUrl('/login');
    localStorage.removeItem('token');
  }

  public listar() {
    this.router.navigateByUrl('users/list');
  }

  public crear() {
    this.router.navigateByUrl('users/create');
  }
}
