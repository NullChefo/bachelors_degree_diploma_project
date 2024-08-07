import { Component, OnInit } from '@angular/core';
import { take } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {

  constructor(private authService: AuthService, private activateRoute: ActivatedRoute, private router: Router) {
    this.getAuthorizationCode();
  }

  ngOnInit(): void {
    this.authService.getToken().pipe(take(1)).subscribe((tokens) => {
      console.log(tokens);
      if ((tokens as any)?.id_token) {
        //   sessionStorage.setItem('id_token', (tokens as any).id_token);
        //    sessionStorage.setItem('refresh_token', (tokens as any).refresh_token);
        this.authService.saveToken(tokens);
        this.router.navigate(['/home']);
      }
    });
  }


  getAuthorizationCode() {
    this.activateRoute.queryParams.subscribe((params) => {
      if (params?.['code']) {
        this.authService.code = params['code'];
      }
    })
  }
}

