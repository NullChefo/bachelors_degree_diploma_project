import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {

  constructor(private authService: AuthService, private activateRoute: ActivatedRoute, private router: Router) {
  }

  ngOnInit(): void {

    let code = this.activateRoute.snapshot.queryParamMap.get('code');
    if (!code) {
      this.authService.login();
      return;
    }

    this.authService.getToken(code).subscribe({
      next: (tokens: any) => {
        if ((tokens as any)?.id_token) {
          this.authService.saveToken(tokens);
          this.router.navigate(['/']);
        }
      },
      error: (err: any) => {
        console.log("Error retrieving tokens: " + err);
        this.authService.login();
      },

    });
  }


}

