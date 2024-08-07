import { EventEmitter, Injectable } from '@angular/core';
import { AUTH_SERVER_URI, CLIENT_ID, CLIENT_SECRET, REDIRECT_URI, SCOPES, AUTHORIZATION_URL, TOKEN_ENDPOINT } from '../../../environments/environment';

import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import { Buffer } from 'buffer';

import pkceChallenge from 'pkce-challenge'
import tokenUrl from 'src/app/constants/token';
import redirectUri from 'src/app/constants/redirect';
import revokeToken from 'src/app/constants/revoke';
import { INTROSPECT_TOKEN_ENDPOINT, REVOKE_ENDPOINT } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public code: string = '';
  userAuthorized = new EventEmitter;
  redirect_url: string = "";


  private code_challenge?: any;
  private code_verifier?: any;
  private basicAuth?: any;

  constructor(
    private http: HttpClient, private cookieService: CookieService, private router: Router) {
    let challenge = pkceChallenge(128);
    this.code_challenge = challenge.code_challenge;
    this.code_verifier = challenge.code_verifier;
    this.basicAuth = `Basic ` + Buffer.from(`${CLIENT_ID}:${CLIENT_SECRET}`).toString('base64');
  }

  /*
      // Get protected resource 
      getResource(resourceUrl: string) : Observable<any>{
        var headers = new HttpHeaders({'Content-type': 'application/x-www-form-urlencoded; charset=utf-8', 'Authorization': 'Bearer '+ this.cookieService.get('access_token')});
        return this._http.get(resourceUrl, { headers: headers }).subscribe(){}
                       .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
      }
    */

  checkCredentials(): boolean {
    if (this.cookieService.check('access_token')) {
      debugger
      return this.introspectToken()
    }
    return false;
  }


  introspectToken(): boolean {
    //   debugger
    //   if(this.cookieService.check('access_token')){
    //                          .check doesn't work for now
    //     debugger
    //     this.userAuthorized.emit(false);
    //     return false;
    //   }
    let accessToken = this.cookieService.get('access_token');

    debugger
    if (!accessToken) {
      this.userAuthorized.emit(false);
      debugger
      return false;

    }
    else {
      debugger


      const headers = new HttpHeaders({
        'Authorization': this.basicAuth
      });
      const options = {
        headers: headers
      }

      const formData = new FormData();
      // append your data
      formData.append('token', accessToken);
      formData.append('client_id', CLIENT_ID);
      formData.append('client_secret', CLIENT_SECRET);

      this.http.post(INTROSPECT_TOKEN_ENDPOINT, formData, options).subscribe({
        next: (value: any) => {
          debugger


          if (value == null || value == "") {
            debugger
            this.userAuthorized.emit(false);
            this.deleteTokens();
            return false;
          }
          debugger
          let parsedString = value.toString();
          let notActive = parsedString.includes("false");
          if (notActive == false) {
            debugger
            this.userAuthorized.emit(true);
            return true;
            debugger
          } else {
            this.userAuthorized.emit(false);
            this.deleteTokens();
            return false;
            debugger
          }

        },

        error: () => {
          debugger
          this.userAuthorized.emit(false);
          this.deleteTokens();
          return false;
        }
      })
      return false;

    }


  }



  deleteTokens() {
    this.cookieService.delete("access_token");
    this.cookieService.delete("id_token");
    this.cookieService.delete("refresh_token");
  }


  logout() {


    let token = this.cookieService.get('access_token');
    this.deleteTokens();
    const headers = new HttpHeaders({
      'Authorization': this.basicAuth
    });
    const options = {
      headers: headers
    }


    const formData = new FormData();
    // append your data
    formData.append('token', token);
    formData.append('client_id', CLIENT_ID);
    formData.append('client_secret', CLIENT_SECRET);
    this.userAuthorized.emit(false);

    return this.http.post(REVOKE_ENDPOINT, formData, options)
      .subscribe({
        next: (value: any) => {
          console.log(value);
          debugger
        },
        error: (error: HttpErrorResponse) => {
          console.log(error);
          debugger
        }
      })

  }


  login() {
    window.location.href = redirectUri(this.code_challenge);
  }

  redirectTo(address: string) {
    this.router.navigate([`${address}`]);
  }

  isUserSighedIn() {

    return this.checkCredentials();

  }



  saveToken(token: any) {
    console.log(token)
    var ex = Date.now() + token.expires_in;

    console.log(token.expires_in);


    // TODO change this for production
    // this.cookieService.set("access_token", token.access_token,{expires: expireDate, secure: true, sameSite: 'None', path: "/" ,  } )
    debugger;
    console.log(ex)
    this.cookieService.set("access_token", token.access_token, { expires: ex, secure: true, sameSite: 'None', path: "/", });
    this.cookieService.set("id_token", token.id_token, { expires: ex, secure: true, sameSite: 'None', path: "/" });
    this.cookieService.set("refresh_token", token.refresh_token, { sameSite: 'None', secure: true, path: "/" });

    this.userAuthorized.emit(true);
  }

  getToken() {
    // TODO fix security

    const headers = new HttpHeaders({
      'content-type': 'application/json',
      'Authorization': this.basicAuth
    });
    const options = {
      headers: headers
    }
    return this.http.post(tokenUrl(this.code), null, options)

  }
}
