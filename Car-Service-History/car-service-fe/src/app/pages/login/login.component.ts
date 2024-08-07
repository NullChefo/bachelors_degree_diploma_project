import { HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Token } from '@angular/compiler';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, retryWhen, Subscription } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import { JwtService } from 'src/app/services/jwt.service';
import { NotificationService } from 'src/app/services/notification.service';
import { UserService } from 'src/app/services/user.service';
import { User, UserLoginDTO } from 'src/app/types/User';

@Component({
  selector: 'login-component',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {


  sub: Subscription = new Subscription;

  error: string = '';

  validEmailOrUsername = true;
  validPassword = true;

  rawTextField = "";

  user: UserLoginDTO = {
    email: '',
    username: "",
    password: '',

  };


  constructor(
    private authService: AuthService,
    private jwtService: JwtService,
    //  private notificationService: NotificationService,
    private activationRoute: ActivatedRoute

  ) { }


  hideErrors(): void {


  }




  ngOnInit(): void {

    this.sub = this.activationRoute.paramMap.subscribe(params => {
      var error = params.get('error');
      if (error) {
        this.error = error.toString();
      }

    }

    )
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  /*
  // TODO make it work with this
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
         
      withCredentials: true, 
      observe: 'response' as 'response'
    };  
    */



  validateForm() {

    const invalidTextField = this.rawTextField == null || this.rawTextField == "";
    const invalidPassword = this.user.password == null || this.user.password == "";

    if (invalidTextField && invalidPassword) {
      this.validEmailOrUsername = false;
      this.validPassword = false;
      this.error = "Enter valid email and password!"
      return;
    }
    if (invalidTextField) {
      this.validEmailOrUsername = false;
      this.error = "Enter valid email!"
      return;
    }
    if (this.user.password == null || this.user.password == "") {
      this.validPassword = false;
      this.error = "Enter valid password";
      
    }
    else{
      this.validPassword = true;
      this.validEmailOrUsername = true;
    }


  }
  cleanForm() {
    this.error = '';
    this.validEmailOrUsername = true;
    this.validPassword = true;

    this.rawTextField = ""; // stefan.kehayov.96@gmail.com

    this.user = {
      email: '',
      username: '',
      password: '',
    };
  }




  logIn(): void {
    

    const isEmail = this.rawTextField.toLowerCase().match(/[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/);
    if (isEmail != null) {
      this.user.email = this.rawTextField;
    }
    else {
      this.user.username = this.rawTextField;
    }

    this.validateForm();

    if (!(this.validEmailOrUsername && this.validPassword)) {
      return;
    }

    /*
    this.userService.loginUser(this.user).subscribe({
        complete: () => { ... }, // completeHandler
        error: () => { ... },    // errorHandler 
        next: () => { ... },     // nextHandler
        someOtherProperty: 42
    });
    
    */


    // TODO get the token and paste in localStorage 

    this.authService.loginUser(this.user).subscribe({

      error: (e) => {
        if (e instanceof HttpErrorResponse) {
          switch (e.status) {

            case 400: // Unauthorized
            this.error = "Not valid form!";
            break;
            case 406: // Wrong password
              this.error = e.error;
              break;
            case 404: // Not found
              this.error = e.error;
              break;

            default:
              this.error = "There is a unexpected problem please  <a class='underline underline-offset-2' href='mailto:support@nullchefo.com'> contact support! </a> "
              break;
          }
        }
      },
      next: (response: any) => {
        this.cleanForm();
        this.jwtService.setTokens(response); // TODO method log in and redirect

        this.authService.redirect();
        console.log(response)  // todo remove


      },
      complete: () => { }, // completeHandler

    });
  }


}
