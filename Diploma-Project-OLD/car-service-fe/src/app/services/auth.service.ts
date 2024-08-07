import { HttpClient } from '@angular/common/http';

import { EventEmitter, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AUTH_PATH_V1 } from 'src/environments/environment';
import { Tokens, UserLoginDTO } from '../types/User';
import { Router } from '@angular/router';
import { JwtService } from './jwt.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {


  userAuthorized = new EventEmitter;


  constructor(private http: HttpClient, private router: Router, private jwtService: JwtService) { }


  loginUser(user: UserLoginDTO): Observable<UserLoginDTO> {
    console.log(AUTH_PATH_V1);
    return this.http.post<UserLoginDTO>(AUTH_PATH_V1 + "/login", user);
  }





  routeToLogin(): void {
    this.router.navigate([`/user/login/You are not logged in`]);
  }

  redirect() {
    this.router.navigate([`/cars/user`]);
    this.userAuthorized.emit(true);
  }

  redirectHome() {
    this.router.navigate([`/`]);
  }

  removeTokenAndRefreshTopPanel(){

    this.jwtService.removeToken();
    this.userAuthorized.emit(false);

  }


  // TODO make more corect check
  isUserSighedIn(): boolean {
    if ( (localStorage.getItem('accessToken') != null && localStorage.getItem('refreshToken') != null) && (localStorage.getItem('accessToken') !="" && localStorage.getItem('refreshToken') !="") ) {
      this.userAuthorized.emit(true);
      return true;
    }
    console.log(false)
    this.userAuthorized.emit(false);
    return false;
  }

  logOut() { // TODO use

    this.logOutRequest().subscribe({

      error: (e) => {
     //   this.routeToLogin();
        console.log(e); // TODO remove
      },
      next: (response) => {

        this.userAuthorized.emit(false); // TODO fix
      }
    })

    this.removeTokenAndRefreshTopPanel();

    this.redirectHome();


  }


  logOutRequest(): Observable<Tokens> {
    return this.http.post<Tokens>(AUTH_PATH_V1 + "/logout", this.jwtService.getTokens());
  }


  getNewAccessToken() {
    const refreshToken = localStorage.getItem('refreshToken');

    if (!refreshToken) {
      return;
    }
    var token: Tokens = {
      accessToken: '',
      refreshToken: refreshToken
    }
    this.jwtService.refreshToken(token).subscribe({

      error: (e) => {
        this.removeTokenAndRefreshTopPanel();
        this.routeToLogin();

        console.log(e);

      },    // errorHandler 

      next: (response: Tokens) => {
        this.jwtService.setTokens(response);
      },
      complete: () => { },
    });
  }
}