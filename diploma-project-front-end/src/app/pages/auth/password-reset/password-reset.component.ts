import {HttpErrorResponse} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {AuthService} from 'src/app/services/auth/auth.service';
import {AUTH_SERVER_URI} from 'src/environments/environment';
import {UserResetPasswordDTO} from 'src/types/Auth';

@Component({
  selector: 'app-password-reset',
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.css']
})
export class PasswordResetComponent implements OnInit {

  loginURL = AUTH_SERVER_URI + '/login';

  error = '';
  isMailValid = true;
  isSend = false;
  resetDTO: UserResetPasswordDTO = {
    email: ''
  }

  constructor(private authService: AuthService) {
  }

  validateForm() {

    if (this.resetDTO.email == undefined) {
      return;
    }

    const isEmail = this.resetDTO.email.toLowerCase().match(/[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/);
    const invalidEmail = this.resetDTO.email == null || this.resetDTO.email == "" || this.resetDTO.email.length < 3 || this.resetDTO.email.length > 50 || isEmail == null;
    if (invalidEmail) {
      this.isMailValid = false;

    } else {
      this.isMailValid = true;
    }

  }

  ngOnInit(): void {
  }


  reset(): void {

    this.validateForm()


    this.authService.sendResetPasswordRequest(this.resetDTO).subscribe({

      error: (error) => {
        if (error instanceof HttpErrorResponse) {
          switch (error.status) {

            case 400: // Unauthorized
              this.error = "Not valid form!";
              break;

            case 404: // Wrong password
              this.error = error.error;
              break;
            case 200: // TODO find fix
              this.isSend = true; // TODO don't redirect
              this.error = "";
              break;
            default:
              // TODO catch more
              this.error = "There is a unexpected problem please  <a class='underline underline-offset-2' href='mailto:support@nullchefo.com'> contact support! </a> "
              break;
          }
        }
      },
      next: () => {

        this.isSend = true; // TODO don't redirect
        this.error = "";

      },
      complete: () => {
      },
    });


  }

  logIn() {
    let message = this.authService.login();
  }

}
