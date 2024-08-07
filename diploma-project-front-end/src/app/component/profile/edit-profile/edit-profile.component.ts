import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from 'src/app/services/auth/auth.service';
import {ProfileService} from 'src/app/services/profile.service';
import {EditUserDTO} from 'src/types/User';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit, OnDestroy {

  error: string = "";
  editProfile: EditUserDTO = {
    id: 0,
    email: "",
    lastName: "",
    firstName: "",
    oauth: false,
    // password: "",
    username: "",
    // confirmPassword: "",
    pronouns: "",

    work: "",
    university: "",
    websiteURL: "",
    about: "",
    avatarURL: "",
    school: "",
    // gender: "",
    phone: "",
    linkedInURL: "",


  };

  constructor(private profileService: ProfileService, private authService: AuthService) {
  }

  ngOnInit(): void {

    this.profileService.getUser().subscribe({

      next: (response: EditUserDTO) => {
        this.mapUserToEditProfile(response);
      },

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
        this.error = error.error.message;
      }

    })
  }


  submit() {

    // validate the values


    // transfer the values from the profile to the edit profile


    this.profileService.updateUser(this.editProfile).subscribe({


      next: (response: EditUserDTO) => {
        // this.profile = response;
        this.authService.redirectToInternalAddress("/profile");


      },

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
        this.error = error.error.message;
      }
    })
  }


  ngOnDestroy(): void {

  }


  mapUserToEditProfile(profile: EditUserDTO) {
    this.editProfile = {
      id: profile.id,
      email: profile.email,
      avatarURL: profile.avatarURL,
      lastName: profile.lastName,
      firstName: profile.firstName,
      oauth: profile.oauth,
      // password: "",
      // confirmPassword: "",
      username: profile.username,
      work: profile.work,
      university: profile.university,
      school: profile.school,
      // gender: profile.gender,
      phone: profile.phone,
      about: profile.about,
      websiteURL: profile.websiteURL,
      linkedInURL: profile.linkedInURL,
      pronouns: profile.pronouns,
    };
  }

}
