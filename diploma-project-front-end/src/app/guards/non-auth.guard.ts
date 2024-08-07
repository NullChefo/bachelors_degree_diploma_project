import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {AuthService} from '../services/auth/auth.service';
import {Subscription} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NonAuthGuard implements CanActivate {
  loadingSub: Subscription = new Subscription;
  isUserLogged = false;

  constructor(private authService: AuthService, private router: Router) {
  }

  canActivate(): boolean {

    this.loadingSub = this.authService.userAuthorized
      .subscribe((status: boolean) => {
        this.isUserLogged = status;
      });
    if (this.isUserLogged == false) {
      return false;
    }

    this.router.navigate([`/feed`]);
    return true;


  }

}
