import { Injectable } from '@angular/core';
import {  ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import { AuthService } from '@core/service/auth.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TokenGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}
  
 canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    const isAuthenticated = this.authService.isAuthenticated();
    if (!isAuthenticated) {
      return this.router.createUrlTree(['/login']);
    }
    return true;
  }
  
  
}
