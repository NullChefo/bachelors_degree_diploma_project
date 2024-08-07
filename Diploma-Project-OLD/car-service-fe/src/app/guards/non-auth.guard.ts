import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class NonAuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router){}

// TODO remove or improve
  canActivate(): boolean{

    if(this.authService.isUserSighedIn()){
      return false;
    }

    this.router.navigate([`/car`]);
    return true;


  }

}