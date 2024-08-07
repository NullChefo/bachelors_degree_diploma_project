import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { UserRegisterDTO, UserResetPasswordDTO } from 'src/app/types/User';

@Component({
  selector: 'app-password-reset',
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.css']
})
export class PasswordResetComponent implements OnInit {

  constructor(private userService: UserService) { }

  error = '';

  isMailValid = true;

  isSend= false;

  resetDTO: UserResetPasswordDTO = {
    email: ''
  }


  validateForm(){

    const isEmail = this.resetDTO.email.toLowerCase().match(/[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/);
    const invalidEmail = this.resetDTO.email == null || this.resetDTO.email == "" || this.resetDTO.email.length < 3 ||   this.resetDTO.email.length > 50 || isEmail == null;
    if(invalidEmail){
      this.isMailValid = false;

    }
    else{
      this.isMailValid = true;
    }

  }

  ngOnInit(): void {
  }


    reset(): void {

      this.validateForm()


      this.userService.sendResetPasswordRequest(this.resetDTO).subscribe({
  
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
        complete: () => { },
      });
    


  }

}
