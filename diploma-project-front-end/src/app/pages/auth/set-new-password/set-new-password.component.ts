import {Component, OnInit} from '@angular/core';

import {HttpErrorResponse} from '@angular/common/http';
import {NewPasswordDTO, ResetPassword} from 'src/types/Auth';
import {Subscription} from 'rxjs';
import {AuthService} from 'src/app/services/auth/auth.service';
import {ActivatedRoute} from '@angular/router';
import {AUTH_SERVER_URI} from 'src/environments/environment';

@Component({
  selector: 'app-set-new-password',
  templateUrl: './set-new-password.component.html',
  styleUrls: ['./set-new-password.component.css']
})
export class SetNewPasswordComponent implements OnInit {
  loginURL = AUTH_SERVER_URI + '/login';
  token = "";
  validationError = "";
  error = "";


  isActivatedSuccessfully = false;

  isTokenValid = true;
  tokenExpired = false;

  isPasswordValid = true;
  isPasswordsMatching = true;
  isMatchingPasswordValid = true;


  resetDTO: ResetPassword = {
    password: '',
    matchingPassword: ''
  }

  newPasswordDTO: NewPasswordDTO = {
    oldPassword: '',
    newPassword: ''
  }

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

    if (this.token == null || this.token == "" || this.token == "error" || this.token == "null") {

      this.isTokenValid = false;

    }


  }

  // TODO find a better way

  validateForm() {


    const invalidPassword = this.resetDTO.password == null || this.resetDTO.password == "";

    if (invalidPassword) {
      this.isPasswordValid = false;
    } else {
      this.isPasswordValid = true;
    }

    const invalidMatchingPassword = this.resetDTO.matchingPassword == null || this.resetDTO.matchingPassword == "";


    if (invalidMatchingPassword) {
      this.isMatchingPasswordValid = false;
    } else {
      this.isMatchingPasswordValid = true;
    }


    if (!(invalidPassword && invalidMatchingPassword) && this.resetDTO.password != this.resetDTO.matchingPassword) {

      this.isPasswordsMatching = false;
    } else {
      this.isPasswordsMatching = true;
    }

  }


  isFormValid(): boolean {
    return this.isPasswordValid && this.isMatchingPasswordValid && this.isPasswordsMatching
  }


  reset(): void {

    this.validateForm()

    if (!this.isFormValid()) {
      return;
    }


    const newPassword: NewPasswordDTO = {
      oldPassword: '',
      newPassword: this.resetDTO.matchingPassword
    }


    this.authService.sendNewPasswordRequest(newPassword, this.token).subscribe({

      error: (error) => {
        if (error instanceof HttpErrorResponse) {
          switch (error.status) {
            case 400: // Wrong password
              this.error = "Not valid form!";
              this.isTokenValid = false;
              break;
            case 403: // Wrong password
              this.error = error.error;
              this.isTokenValid = false;
              break;
            case 200: // Wrong password
              this.isActivatedSuccessfully = true;
              break;
            default:
              // TODO catch more
              this.error = "There is a unexpected problem please  <a class='underline underline-offset-2' href='mailto:support@nullchefo.com'> contact support! </a> "
              this.isActivatedSuccessfully = false;
              break;
          }
        }
      },
      next: () => {
        this.isActivatedSuccessfully = true;

      },
      complete: () => {
      },
    });


  }

  logIn() {
    let message = this.authService.login();
  }
}
