import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Observable, tap} from "rxjs";
import {AuthService} from "../services/auth/auth.service";

@Injectable()
export class CustomInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const token = this.authService.getAccessToken();
    let authHeader = request.headers.get("Authorization");

    // console.log("authHeader: " + authHeader);
    // console.log("token: " + token);

    if (authHeader?.length != undefined && authHeader?.length < 20) {
      authHeader = null;
    }


    if (authHeader != null) {

      request = request.clone({
        setHeaders: {
          // 'Content-Type': 'application/json; charset=utf-8',
          // Accept: 'application/json',
          Authorization: authHeader
        }
      });


    } else if (token != undefined && token != "" && token != null) {


      request = request.clone({
        setHeaders: {
          // 'Content-Type': 'application/json; charset=utf-8',
          // Accept: 'application/json',
          Authorization: `Bearer ${token}`
        }
      });


    } else {


      request = request.clone({
        setHeaders: {
          // 'Content-Type': 'application/json; charset=utf-8',
          // Accept: 'application/json'
        }
      });


    }
    return next
      .handle(request).pipe(
        tap((ev: HttpEvent<any>) => {

            (error: any) => {
              if (error instanceof HttpErrorResponse) {
                if (error.status === 401) {
                  this.authService.getNewAccessToken();
                  this.authService.checkCredentials();
                }
                //   if (error.status === 403) {
                //     this.authService.routeToLogin();
                //   }
              }
            }
          },
        ),
      );
  }
}
