import {EventEmitter, Injectable} from '@angular/core';
import {
  AUTH_SERVER_URI,
  CLIENT_ID,
  CLIENT_SECRET,
  INTROSPECT_TOKEN_ENDPOINT,
  REVOKE_ENDPOINT,
  USER_PATH_V1,
  USER_PATH_V2
} from '../../../environments/environment';

import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from '@angular/common/http';
import {CookieService} from 'ngx-cookie-service';
import {Router} from '@angular/router';
import {Buffer} from 'buffer';

import pkceChallenge from 'pkce-challenge'
import tokenUrl from 'src/app/constants/token';
import redirectUri from 'src/app/constants/redirect';
import {NewPasswordDTO, User, UserRegisterDTO, UserResetPasswordDTO} from 'src/types/Auth';
import {Observable} from 'rxjs';
import register from 'src/app/constants/register';
import {UserRetrieveDTO} from 'src/types/User';


@Injectable({
  providedIn: 'root'
})
export class AuthService {


  public code: string = '';
  public isLoggedIn = false;
  userAuthorized = new EventEmitter;
  redirect_url: string = "";
  private code_challenge?: any;
  private code_verifier?: any;
  private basicAuth?: any;

  private userLoggedIn = false;

  constructor(
    private http: HttpClient, private cookieService: CookieService, private router: Router) {
    let challenge = pkceChallenge(128);
    this.code_challenge = challenge.code_challenge;
    this.code_verifier = challenge.code_verifier;

    // TODO CommonJS or AMD dependencies can cause optimization bailouts
    const data = `${CLIENT_ID}:${CLIENT_SECRET}`;
    this.basicAuth = `Basic ` + Buffer.from(data).toString('base64');
  }

  registerRedirect() {
    window.location.href = register();
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
    let accessToken = this.cookieService.get('access_token');
    if (!accessToken) {
      this.userAuthorized.emit(false);
      this.userLoggedIn = false;
      return false;
    }
    return this.introspectToken();
  }


  checkCredentialsForGuard(): boolean {
    let accessToken = this.cookieService.get('access_token');
    if (!accessToken) {
      this.userAuthorized.emit(false);
      this.userLoggedIn = false;
      return false;
    }
    return true;
  }

  introspectToken(): boolean {
    let accessToken = this.cookieService.get('access_token');

    //  debugger
    if (!accessToken) {
      this.userAuthorized.emit(false);
      this.userLoggedIn = false;
      this.deleteTokens();
      return false;

    } else {
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
          if (value == null || value == "") {
            //            debugger
            this.userAuthorized.emit(false);
            this.userLoggedIn = false;
            this.deleteTokens();
            return false;
          }

          let active = value?.active;

          console.log("Active: " + active);

          if (active == true) {
            this.userAuthorized.emit(true);
            this.userLoggedIn = true;
            return true;
          } else {
            this.userAuthorized.emit(false);
            this.userLoggedIn = false;
            this.deleteTokens();
            return false;
          }
        },
        error: () => {
          //     debugger
          this.userAuthorized.emit(false);
          this.userLoggedIn = false;
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
    this.cookieService.delete("INGRESSCOOKIE");
    this.cookieService.delete("JSESSIONID");
  }

  // setCookieCurrentUser(currentUser: UserRetrieveDTO | undefined){
  //     if (currentUser == undefined){
  //         console.log("User is undefined");
  //         return;
  //     }

  //     this.cookieService.set("current_user", currentUser.toString(), {sameSite: 'None', secure: true, path: "/"})
  // }

  // getCookieCurrentUser(): UserRetrieveDTO | undefined{
  //     let currentUser = this.cookieService.get("current_user");
  //     if(currentUser == undefined){
  //         return undefined;
  //     }
  //     return JSON.parse(currentUser);
  // }

  getAccessToken(): string {
    return this.cookieService.get("access_token");
  }


  logout(): any {
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
    this.userLoggedIn = false;

    this.redirectToExternalAddress(AUTH_SERVER_URI + '/logout');
    return this.http.post(REVOKE_ENDPOINT, formData, options)
      .subscribe({
        next: (value: any) => {


        },
        error: (error: HttpErrorResponse) => {
          console.log(error);

        },
        complete: () => {
          this.redirectToExternalAddress(AUTH_SERVER_URI + '/logout');

        }
      })


  }


  login() {
    window.location.href = redirectUri(this.code_challenge);
  }

  redirectToInternalAddress(address: string) {
    this.router.navigate([`${address}`]);
  }

  redirectToExternalAddress(address: string) {
    window.location.href = address;
  }

  isUserSighedIn() {
    let isSignedIn = this.checkCredentials();
    console.log("Is user signed in: " + isSignedIn);
    return isSignedIn;
  }


  saveToken(token: any) {
    var ex = Date.now() + token.expires_in;
    this.cookieService.set("access_token", token.access_token, {
      expires: ex,
      secure: true,
      sameSite: 'None',
      path: "/",
    });
    this.cookieService.set("id_token", token.id_token, {expires: ex, secure: true, sameSite: 'None', path: "/"});
    this.cookieService.set("refresh_token", token.refresh_token, {sameSite: 'None', secure: true, path: "/"});
    this.userAuthorized.emit(true);
    this.userLoggedIn = true;
  }

  getToken(token: string): Observable<any> {

    // TODO fix security

    const headers = new HttpHeaders({
      'content-type': 'application/json',
      'Authorization': this.basicAuth
    });
    const options = {
      headers: headers
    }
    return this.http.post(tokenUrl(token), null, options)
  }

  getNewAccessToken(): boolean {
    // TODO spring authorization server send refresh token
    let isLogged = this.checkCredentials();
    return isLogged;
  }


  /* More general use case */

  // TODO secure; only the user-id can perform or higher Authority
  editUser(user: User): Observable<any> {
    return this.http.put(USER_PATH_V1 + '/' + user.id, user);
  }

  // TODO secure; only the user-id can perform or higher Authority
  // Uses the request
  importUser(user: User): Observable<any> {
    return this.http.post(USER_PATH_V1 + '/import', user);

  }

  // TODO secure; only the user-id can perform or higher Authority
  deleteUser(id: string): Observable<any> {
    return this.http.delete(USER_PATH_V1 + '/' + id);
  }


  // TODO secure; only specific role
  getUserById(userId?: string): Observable<User> {
    return this.http.get<User>(USER_PATH_V1 + "/" + userId);
  }

  register(userRegisterDTO: UserRegisterDTO) {
    return this.http.post<UserRegisterDTO>(USER_PATH_V2 + "/management/register", userRegisterDTO);
  }

  sendResetPasswordRequest(resetDTO: UserResetPasswordDTO): Observable<UserResetPasswordDTO> {
    return this.http.post<UserResetPasswordDTO>(USER_PATH_V2 + "/resetPassword", resetDTO);
  }

  validateRegistrationToken(token: string): Observable<Object> {
    const param = new HttpParams().append('token', token)
    return this.http.get(USER_PATH_V2 + "/management/verifyRegistration", {params: param});
  }


  getNewRegistrationToken(oldToken: string) {
    const param = new HttpParams().append('token', oldToken)
    return this.http.get(USER_PATH_V2 + "/management/resendVerifyToken", {params: param});

  }

  sendNewPasswordRequest(resetDTO: NewPasswordDTO, token: string) {
    const param = new HttpParams().append('token', token)
    return this.http.post(USER_PATH_V2 + "/management/savePassword", resetDTO, {params: param});

  }


  getUserId(): number | null {

    let token = this.cookieService.get("access_token");

    if (token == null) {
      return null;
    }

    // Split the token into its three parts
    let parts = token.split('.');

    // Decode the payload without verifying the signature
    let payload = JSON.parse(atob(parts[1]));

    // TODO make sure authorization server passes this value
    // Access the user_id property
    let userId = payload.user_id;

    return userId;

  }


  getUserUsername(): string | null {

    let token = this.cookieService.get("access_token");

    if (token == null) {
      return null;
    }

    // Split the token into its three parts
    let parts = token.split('.');

    // Decode the payload without verifying the signature
    let payload = JSON.parse(atob(parts[1]));

    // Access the username property
    let username = payload.sub;

    return username;
  }


  isUserLoggedIn(): boolean {
    return this.userLoggedIn;

  }


  getCurrentUserRestCall(): Observable<UserRetrieveDTO> {
    return this.http.get<UserRetrieveDTO>(USER_PATH_V2 + "/currentUser");
  }

}
