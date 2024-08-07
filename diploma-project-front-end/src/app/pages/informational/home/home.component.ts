import {Component, OnInit} from '@angular/core';
import {AuthService} from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  projectName: string = "Diploma Project";

  isUserSignedIn: boolean = false;

  // loadingSub: Subscription = new Subscription;

  isUserLogged = false;

  constructor(private authService: AuthService) {
  }

  ngOnDestroy(): void {
    // this.loadingSub.unsubscribe();
  }

  ngOnInit() {

    this.isUserSignedIn = this.authService.isUserLoggedIn();
    console.log("isUserSignedIn : " + this.isUserSignedIn);

    // this.loadingSub = this.authService.userAuthorized
    //     .subscribe((status: boolean) => {
    //         this.isUserLogged = status;
    //     });

  }
}
