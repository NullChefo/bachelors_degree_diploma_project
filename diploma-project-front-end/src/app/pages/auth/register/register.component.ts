import {Component, OnInit} from '@angular/core';
import {UserRegisterDTO} from 'src/types/Auth';
import {AuthService} from 'src/app/services/auth/auth.service';
import {AUTH_SERVER_URI} from 'src/environments/environment';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  authorizationServer = AUTH_SERVER_URI;
  error = "";

  isMailValid = true;
  isUsernameValid = true;
  isFirstNameValid = true;
  isLastNameValid = true;
  isBirthdayValid = true;

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
    pronouns: '',


    signedForAnnouncements: false,
    signedForPromotions: false,
    signedForNotifications: false
  }


  constructor(private authService: AuthService) {
  }

  ngOnInit(): void {
  }

  validateForm() {

    if (this.userRegisterDTO.email == undefined) {
      return;
    }

    let isEmail = this.userRegisterDTO.email.toLowerCase().match(/[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/);


    let invalidEmail = this.userRegisterDTO.email == null || this.userRegisterDTO.email == "" || this.userRegisterDTO.email.length < 3 || this.userRegisterDTO.email.length > 50 || isEmail == null;

    if (invalidEmail) {
      this.isMailValid = false;

    } else {
      this.isMailValid = true;
    }


    let invalidUsername = this.userRegisterDTO.username == null || this.userRegisterDTO.username == "" || this.userRegisterDTO.username.length > 50;

    if (invalidUsername) {
      this.isUsernameValid = false;
    } else {
      this.isUsernameValid = true;
    }

    let invalidFirstName = this.userRegisterDTO.firstName == null || this.userRegisterDTO.firstName == "" || this.userRegisterDTO.firstName.length > 50;


    if (invalidFirstName) {
      this.isFirstNameValid = false;
    } else {
      this.isFirstNameValid = true;
    }


    let invalidLastName = this.userRegisterDTO.lastName == null || this.userRegisterDTO.lastName == "" || this.userRegisterDTO.lastName.length > 50;

    if (invalidLastName) {
      this.isLastNameValid = false;
    } else {
      this.isLastNameValid = true;
    }


    let invalidPassword = this.userRegisterDTO.password == null || this.userRegisterDTO.password == "";

    if (invalidPassword) {
      this.isPasswordValid = false;
    } else {
      this.isPasswordValid = true;
    }

    let invalidMatchingPassword = this.userRegisterDTO.matchingPassword == null || this.userRegisterDTO.matchingPassword == "";


    if (invalidMatchingPassword) {
      this.isMatchingPasswordValid = false;
    } else {
      this.isMatchingPasswordValid = true;
    }


    if (!(invalidPassword && invalidMatchingPassword) && this.userRegisterDTO.password != this.userRegisterDTO.matchingPassword) {

      this.isPasswordsMatching = false;
    } else {
      this.isPasswordsMatching = true;
    }


    // let invalidBirthday = this.userRegisterDTO.birthday == undefined;
    // if (invalidBirthday) {
    //     this.isBirthdayValid = false;
    // } else {

    //     if (this.userRegisterDTO.birthday == undefined || this.userRegisterDTO.birthday == null) {
    //         this.isBirthdayValid = false;
    //         return;
    //     }

    //     let userBirthday = new Date(this.userRegisterDTO.birthday);

    //     if (this.isDate18orMoreYearsOld(userBirthday.getDay(), userBirthday.getMonth(), userBirthday.getFullYear())) {
    //         this.isBirthdayValid = true;
    //     } else {
    //         this.isBirthdayValid = false;

    //     }

    // }

  }

  isDate18orMoreYearsOld(day: number, month: number, year: number) {
    return new Date(year + 18, month - 1, day) <= new Date();
  }

  isFormValid(): boolean {
    return this.isMailValid && this.isUsernameValid && this.isFirstNameValid && this.isLastNameValid && this.isPasswordValid && this.isMatchingPasswordValid && this.isPasswordsMatching && this.isBirthdayValid;
  }

  resetForm() {

    this.error = "";

    this.isMailValid = true;
    this.isUsernameValid = true;
    this.isFirstNameValid = true;
    this.isLastNameValid = true;

    this.isPasswordValid = true;
    this.isMatchingPasswordValid = true;
    this.isPasswordsMatching = true;
    this.isBirthdayValid = true;

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

    if (!this.isFormValid()) {
      return;
    }


    this.authService.register(this.userRegisterDTO).subscribe({

      error: (error: any) => {
        if (error.status === 400) {
          // Handle bad request error
        } else if (error.status === 401) {
          // Handle unauthorized error
        } else if (error.status === 404) {
          // Handle not found error
        } else {
          // Handle other errors
        }


        if (error.errorMessage != undefined) {
          this.error = error.errorMessage;
        } else if (error.error.message != undefined) {
          this.error = error.error.message;
        } else {
          this.error = error;
        }
      },
      next: () => {
        this.resetForm();
        this.authService.redirectToInternalAddress("/auth/thanks-for-registration");
      },
      complete: () => {
      },
    });
  }

  logIn() {
    let message = this.authService.login();
  }

}





