import {Component, OnDestroy, OnInit} from '@angular/core';
import {
  faCarAlt,
  faHome,
  faRightFromBracket,
  faRightToBracket,
  faUser,
  faUserPlus
} from '@fortawesome/free-solid-svg-icons';
import {Subscription} from 'rxjs';
import {AuthService} from 'src/app/services/auth/auth.service';
import {environment} from 'src/environments/environment';

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
  hidden = true;

  loadingSub: Subscription = new Subscription;

  isUserLogged = false;
  inDevelopment: boolean = environment.inDevelopment;

  constructor(private authService: AuthService) {
  }

  ngOnDestroy(): void {
    this.loadingSub.unsubscribe();
  }

  ngOnInit() {
    this.loadingSub = this.authService.userAuthorized
      .subscribe((status: boolean) => {
        this.isUserLogged = status;
      });
  }

  logOut(): void {
    this.authService.logout();
  }

  logIn() {
    let message = this.authService.login();
  }

  register() {
    let message = this.authService.registerRedirect();
  }

  listHamburgerMenu() {
    this.hidden = !this.hidden;
    this.setHiddenToElementWithId(this.hidden, "navbar-solid-bg");
  }


  setHiddenToElementWithId(hidden: boolean, id?: string,) {
    if (!id) {
      return;
    }

    const element = document.getElementById(id);
    if (hidden) {
      element?.classList.add("hidden");
    } else {
      element?.classList.remove("hidden");
    }
  }

}
