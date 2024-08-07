import {HttpErrorResponse} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs';
import {AuthService} from 'src/app/services/auth/auth.service';
import {AUTH_SERVER_URI} from 'src/environments/environment';

@Component({
  selector: 'app-validate-registration',
  templateUrl: './validate-registration.component.html',
  styleUrls: ['./validate-registration.component.css']
})
export class ValidateRegistrationComponent implements OnInit {

  loginURL = AUTH_SERVER_URI + "/login";

  token = "";
  validationError = "";
  sendError = "";

  isActivatedSuccessfully = false;

  tokenExpired = false;


  sub: Subscription = new Subscription;

  constructor(private authService: AuthService,
              private activationRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.sub = this.activationRoute.paramMap.subscribe(params => {
        var token = params.get('token');
        if (token) {
          this.token = token.toString();
        }
      }
    );
    this.validateToken();


  }


  validateToken() {


    if (this.token == null && this.token == "") {

      this.validationError = "No valid token";

    }

    this.authService.validateRegistrationToken(this.token).subscribe({


      error: (error) => {
        if (error instanceof HttpErrorResponse) {
          switch (error.status) {
            case 403: // Wrong password
              this.tokenExpired = true;
              break;
            case 200:
              this.validationError = "";
              this.authService.redirectToExternalAddress(this.loginURL); // TODO move to const
              break;
            default:
              // TODO catch more
              this.validationError = "There is a unexpected problem please  <a class='underline underline-offset-2' href='mailto:support@nullchefo.com'> contact support! </a> "
              break;
          }
        }
      },
      next: () => {
        this.validationError = "";
        this.authService.redirectToExternalAddress(this.loginURL); // TODO move to const
      },
      complete: () => {
      },
    });


  }

  sendNewRegisterToken() {

    if (!this.tokenExpired) {
      return;
    }

    this.authService.getNewRegistrationToken(this.token).subscribe({

      error: (error) => {
        if (error instanceof HttpErrorResponse) {
          switch (error.status) {
            case 403: // Not valid token
              this.sendError = error.error;
              break;
            case 200:
              this.authService.redirectToExternalAddress(this.loginURL);
              break;
            default:
              // TODO catch more
              this.sendError = "There is a unexpected problem please  <a class='underline underline-offset-2' href='mailto:support@nullchefo.com'> contact support! </a> "
              break;
          }
        }
      },
      next: () => {
        this.authService.login();
      },
      complete: () => {
      },
    });


  }

  logIn() {
    let message = this.authService.login();
  }


}
