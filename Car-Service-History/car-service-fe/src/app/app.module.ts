import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { RouterModule } from '@angular/router';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

import { LoginComponent } from './pages/login/login.component';
import { AppRoutingModule } from './app-routing.module';
import { LeftPanelComponent } from './components/panels/left-panel/left-panel.component';
import { TopPanelComponent } from './components/panels/top-panel/top-panel.component';

import { HomeComponent } from './pages/home/home.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { environment } from '../environments/environment';
import { CustomInterceptor } from './utils/CustomInterceptor';
import { PageNotFoundComponent } from './pages/auth/page-not-found/page-not-found.component';


import { SetNewPasswordComponent } from './components/auth/set-new-password/set-new-password.component';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

import { CarAddComponent } from './components/car/car-add/car-add.component';
import { EventAddComponent } from './components/event/event-add/event-add.component';
import { EventAllComponent } from './components/event/event-all/event-all.component';
import { ThanksForRegistrationComponent } from './components/auth/thanks-for-registration/thanks-for-registration.component';
import { ValidateRegistrationComponent } from './components/auth/validate-registration/validate-registration.component';
import { CarAllComponent } from './components/car/car-all/car-all.component';

import { PasswordResetComponent } from './pages/auth/password-reset/password-reset.component';
import { RegisterComponent } from './pages/auth/register/register.component';




@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    LeftPanelComponent,
    TopPanelComponent,
    HomeComponent,
    PageNotFoundComponent,
    ThanksForRegistrationComponent,
    ValidateRegistrationComponent,
    PasswordResetComponent,
    SetNewPasswordComponent,
    CarAllComponent,
    CarAddComponent,
    EventAddComponent,
    EventAllComponent,
  

  ],
  imports: [
		BrowserModule,
		HttpClientModule,
		AppRoutingModule,
		FormsModule,
		ReactiveFormsModule,
 // 	BrowserAnimationsModule,
//		NgbModalModule,
    RouterModule,
 FontAwesomeModule,
//   FontAwesomeModule


  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: CustomInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
