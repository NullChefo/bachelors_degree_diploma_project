import { Component, OnDestroy, OnInit } from '@angular/core';
import { faUser, faCarAlt, faHome, faRightFromBracket, faRightToBracket, faUserPlus } from '@fortawesome/free-solid-svg-icons';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/services/auth/auth.service';
@Component({
  selector: 'app-top-panel',
  templateUrl: './top-panel.component.html',
  styleUrls: ['./top-panel.component.css']
})
export class TopPanelComponent implements OnInit, OnDestroy {
  faUser = faUser;
  faCar = faCarAlt;
  faHome = faHome;
  faLogOut = faRightFromBracket;
  faLogIn = faRightToBracket;
  faRegister = faUserPlus;

  loadingSub: Subscription = new Subscription;

  isUserLogged = false;

  constructor(private authService: AuthService) { }

  ngOnDestroy(): void {
    this.loadingSub.unsubscribe();
  }

  ngOnInit() {
    this.authService.isUserSighedIn();

    this.loadingSub = this.authService.userAuthorized
      .subscribe((status: boolean) => {
        this.isUserLogged = status;
      });
  }

  logOut(): void {
    this.authService.logout();
  }

  logIn(){
    let message = this.authService.login();
  }

}
