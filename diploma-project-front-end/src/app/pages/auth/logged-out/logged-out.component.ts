import {Component, OnInit} from '@angular/core';
import {AuthService} from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-logged-out',
  templateUrl: './logged-out.component.html',
  styleUrls: ['./logged-out.component.css']
})
export class LoggedOutComponent implements OnInit {

  constructor(private authService: AuthService) {
  }

  ngOnInit(): void {
    this.authService.deleteTokens();
    this.authService.userAuthorized.emit(false);
    this.authService.redirectToInternalAddress('/');
  }

}
