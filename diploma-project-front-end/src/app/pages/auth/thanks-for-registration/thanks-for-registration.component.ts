import {Component} from '@angular/core';
import {faEnvelope} from '@fortawesome/free-regular-svg-icons';
import {AuthService} from 'src/app/services/auth/auth.service';
import {AUTH_SERVER_URI} from 'src/environments/environment';

@Component({
  selector: 'app-thanks-for-registration',
  templateUrl: './thanks-for-registration.component.html',
  styleUrls: ['./thanks-for-registration.component.css']
})
export class ThanksForRegistrationComponent {
  faEnvelope = faEnvelope;
  loginURL = AUTH_SERVER_URI + '/login';

  constructor(private authService: AuthService) {
  }

  logIn() {
    let message = this.authService.login();
  }

}
