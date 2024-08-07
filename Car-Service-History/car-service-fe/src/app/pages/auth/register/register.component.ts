import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { UserRegisterDTO } from 'src/app/types/User';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {


  error = "";

  isMailValid = true;
  isUsernameValid = true;
  isFirstNameValid = true;
  isLastNameValid = true;

  isPasswordValid = true;
  isMatchingPasswordValid = true;
  isPasswordsMatching = true;

  userRegisterDTO: UserRegisterDTO = {
    email: '',
    password: '',
    matchingPassword: '',
    firstName: '',
    lastName: '',
    username: '',

    signedForAnnouncements: false,
    signedForPromotions: false,
    signedForNotifications: false
  }


  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  validateForm(){



    const isEmail = this.userRegisterDTO.email.toLowerCase().match(/[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/);


    const invalidEmail = this.userRegisterDTO.email == null || this.userRegisterDTO.email == "" || this.userRegisterDTO.email.length < 3 ||   this.userRegisterDTO.email.length > 50 || isEmail == null;

    if(invalidEmail){
      this.isMailValid = false;

    }
    else{
      this.isMailValid = true;
    }

  
    const invalidUsername = this.userRegisterDTO.username  == null || this.userRegisterDTO.username == "" ||   this.userRegisterDTO.username.length > 50;

    if(invalidUsername){
      this.isUsernameValid = false;
    }
    else{
      this.isUsernameValid = true;
    }

    const invalidFirstName = this.userRegisterDTO.firstName  == null || this.userRegisterDTO.firstName == "" ||   this.userRegisterDTO.firstName.length > 50;


    if(invalidFirstName){
      this.isFirstNameValid = false;
    }
    else{
      this.isFirstNameValid = true;
    }


    const invalidLastName = this.userRegisterDTO.lastName  == null || this.userRegisterDTO.lastName == "" ||   this.userRegisterDTO.lastName.length > 50;

    if(invalidLastName){
      this.isLastNameValid = false;
    }
    else{
      this.isLastNameValid = true;
    }


    const invalidPassword =  this.userRegisterDTO.password  == null || this.userRegisterDTO.password == "";

    if(invalidPassword){
      this.isPasswordValid = false;
    }
    else{
      this.isPasswordValid = true;
    }

    const invalidMatchingPassword =  this.userRegisterDTO.matchingPassword  == null || this.userRegisterDTO.matchingPassword == "";


    if(invalidMatchingPassword){
      this.isMatchingPasswordValid = false;
    }
    else{
      this.isMatchingPasswordValid = true;
    }


    if ( !(invalidPassword && invalidMatchingPassword) &&  this.userRegisterDTO.password != this.userRegisterDTO.matchingPassword ){

        this.isPasswordsMatching = false;
    }
      else{
        this.isPasswordsMatching = true;
      }

    }

  

  isFormValid(): boolean{
    return  this.isMailValid  &&  this.isUsernameValid  &&   this.isFirstNameValid &&     this.isLastNameValid &&     this.isPasswordValid && this.isMatchingPasswordValid &&  this.isPasswordsMatching 
  }

  resetForm(){

    this.error = "";

    this.isMailValid = true;
    this.isUsernameValid = true;
    this.isFirstNameValid = true;
    this.isLastNameValid = true;
  
    this.isPasswordValid = true;
    this.isMatchingPasswordValid = true;
    this.isPasswordsMatching = true;

    this.userRegisterDTO = {
      email: '',
      password: '',
      matchingPassword: '',
      firstName: '',
      lastName: '',
      username: '',
      signedForAnnouncements: false,
      signedForPromotions: false,
      signedForNotifications: false
    }
  


  }



  register(): void {


    this.validateForm();

    if(!this.isFormValid()){
    return;
    }


    this.userService.register(this.userRegisterDTO).subscribe({



      error: (error) => {
        if (error instanceof HttpErrorResponse) {
        
          switch (error.status) {
            case 400: // Wrong password
            this.error = "No valid form";
            break;
            case 409: // Wrong password
              this.error = error.error;
              break;
            default:
              this.error = "There is a unexpected problem please  <a class='underline underline-offset-2' href='mailto:support@nullchefo.com'> contact support! </a> "
              break;
          }
        }
      },
      next: () => {
        this.resetForm();
        this.userService.redirectTo("/user/thanks-for-registration"); 
      },
      complete: () => { },
    });
  }

}






