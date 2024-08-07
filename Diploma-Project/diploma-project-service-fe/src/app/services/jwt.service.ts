import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AUTH_PATH_V1 } from 'src/environments/environment';
import { Tokens } from '../types/User';

import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class JwtService {


  constructor(private http: HttpClient) { }


  refreshToken(token: Tokens): Observable<Tokens> {
    
    return this.http.post<Tokens>(AUTH_PATH_V1 + "/refresh-token", token);
  }


  getToken(): string | null { // TODO make it proper
    return localStorage.getItem('accessToken');
  }

  getTokens(): Tokens | null {

    const accessToken = localStorage.getItem('accessToken');
    const refreshToken = localStorage.getItem('refreshToken');

    if(accessToken == null && refreshToken == null || accessToken == "" && refreshToken == ""  || accessToken == undefined && refreshToken == undefined){
      return null;
    }

    var token: Tokens ={
      accessToken: accessToken!,
      refreshToken: refreshToken!
    }
    return token;

  }


  setTokens(tokens: Tokens): void {
    // TODO save the token
    localStorage.setItem('accessToken', tokens.accessToken);
    localStorage.setItem('refreshToken', tokens.refreshToken);
  }

  removeToken() {
    // TODO save the token
    localStorage.setItem('accessToken', "");
    localStorage.setItem('refreshToken', "");

  //  this.authService.userAuthorized.emit(false); //get them out

  }

  decodeToken(){
    var token = this.getToken();

    if(token == null || token == "" ){
      return;
    }
    var parsedToken = atob(token.split('.')[1]);  // use  return JSON.parse(Buffer.from(token.split('.')[1], 'base64').toString())

   //  console.log( JSON.parse(parsedToken)); // TODO remove

    return JSON.parse(parsedToken);
  }

  getUserId(): number | null{

    var userId = this.decodeToken()["userId"];
    if (userId == null || userId == ""){
      return null;
    }

    return userId;
  }





}
