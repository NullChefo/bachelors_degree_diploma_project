import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from "@angular/common/http";
import {Injectable} from "@angular/core";
import { catchError, Observable, tap } from "rxjs";
import { AuthService } from "../services/auth.service";
import { JwtService } from "../services/jwt.service";

    
@Injectable()
export class CustomInterceptor implements HttpInterceptor {
    
  constructor(private jwtService: JwtService , private authService: AuthService) {}
    
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

   const yourToken = this.jwtService.getToken();

    if (yourToken) {

      request = request.clone({
          setHeaders: {
              'Content-Type': 'application/json; charset=utf-8',
              Accept: 'application/json',
              Authorization: `Bearer ${yourToken}`
          }
      });

  } else {

      request = request.clone({
          setHeaders: {
              'Content-Type': 'application/json; charset=utf-8',
              Accept: 'application/json'
          }
      });

  }
    
 //   console.log("outgoing request",request);
    
 //   request = request.clone({ withCredentials: true });
 //     console.log("new outgoing request",request);

      return next
        .handle(request).pipe(
        tap((ev: HttpEvent<any>) => {
        //  console.log("got an event",ev)
          if (ev instanceof HttpResponse) {
           // console.log('event of type response', ev);
          }
      
          (error:any) => { 

            if (error instanceof HttpErrorResponse) {  
              if (error.status === 401) {  
                this.authService.getNewAccessToken();  
                this.authService.isUserSighedIn();
              }  
              if (error.status === 403) {  
                this.authService.routeToLogin();
              }  
            }  
          }
        
        
      },
      ),
      );
  }
}
