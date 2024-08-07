import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-validate-registration',
  templateUrl: './validate-registration.component.html',
  styleUrls: ['./validate-registration.component.css']
})
export class ValidateRegistrationComponent implements OnInit {

  token = "";
  validationError = "";
  sendError = "";

  isActivatedSuccessfully = false;

  tokenExpired = false;


  sub: Subscription = new Subscription;

  constructor(private userService: UserService,
    private activationRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.sub = this.activationRoute.paramMap.subscribe(params => {
      var token = params.get('token');
      if (token) {
        this.token = token.toString();
      }
    }

    );
    this.validateToken();
    console.log(this.token);


  }



  validateToken() {


    if (this.token == null && this.token == "") {

      this.validationError = "No valid token";

    }

    this.userService.validateRegistrationToken(this.token).subscribe({


      error: (error) => {
        if (error instanceof HttpErrorResponse) {
          switch (error.status) {
            case 403: // Wrong password
              this.tokenExpired = true;
              break;
            default:
              // TODO catch more
              this.validationError = "There is a unexpected problem please  <a class='underline underline-offset-2' href='mailto:support@nullchefo.com'> contact support! </a> "
              break;
          }
        }
      },
      next: () => {
        // this.userService.redirectTo("/user/login"); // TODO move to const
        this.validationError = "";
      },
      complete: () => { },
    });




  }

  sendNewRegisterToken() {

    if (!this.tokenExpired) {
      return;
    }

    this.userService.getNewRegistrationToken(this.token).subscribe({

      error: (error) => {
        if (error instanceof HttpErrorResponse) {
          switch (error.status) {
            case 403: // Not valid token
              this.sendError = error.error;
              break;
            default:
              // TODO catch more
              this.sendError = "There is a unexpected problem please  <a class='underline underline-offset-2' href='mailto:support@nullchefo.com'> contact support! </a> "
              break;
          }
        }
      },
      next: () => {
        this.userService.redirectTo("/user/login"); // TODO move to const
      },
      complete: () => { },
    });


  }


}
