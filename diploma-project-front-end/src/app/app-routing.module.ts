import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthComponent} from './component/auth/auth.component';
import {FeedComponent} from './component/feed/feed.component';
import {LoggedOutComponent} from './pages/auth/logged-out/logged-out.component';
import {PasswordResetComponent} from './pages/auth/password-reset/password-reset.component';
import {SetNewPasswordComponent} from './pages/auth/set-new-password/set-new-password.component';
import {ThanksForRegistrationComponent} from './pages/auth/thanks-for-registration/thanks-for-registration.component';
import {ValidateRegistrationComponent} from './pages/auth/validate-registration/validate-registration.component';
import {HomeComponent} from './pages/informational/home/home.component';
import {PrivacyPolicyComponent} from './pages/informational/privacy-policy/privacy-policy.component';
import {TermsOfServiceComponent} from './pages/informational/terms-of-service/terms-of-service.component';
import {PageNotFoundComponent} from './pages/informational/page-not-found/page-not-found.component';
import {ConnectionsComponent} from './pages/connections/connections.component';
// import { CustomInterceptor } from './utils/CustomInterceptor';
import {AuthGuard} from './guards/auth.guard';
import {DisclaimersComponent} from './pages/informational/disclaimers/disclaimers.component';
import {ViewProfileComponent} from './component/profile/view-profile/view-profile.component';
import {EditProfileComponent} from './component/profile/edit-profile/edit-profile.component';
import {ChatPageComponent} from './component/chat/chat-page/chat-page.component';
import {GroupPageComponent} from './component/group/group-page/group-page.component';
import {RegisterComponent} from './pages/auth/register/register.component';
import {DeveloperModeComponent} from './pages/informational/developer-mode/developer-mode.component';
import {DeveloperModeGuard} from './guards/developer-mode.guard';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: HomeComponent
  },


  // {
  //   path: 'auth/authorized',
  //   canActivate: [NonAuthGuard],
  //      children: [

  // { path: '', component: AuthorizedRedirectComponent  },  // canActivate: [NonAuthGuard]
  //   { path: ':code', component: AuthorizedRedirectComponent  }, // canActivate: [NonAuthGuard]

  //          { path: 'auth/authorized', redirectTo: "auth", pathMatch: 'full'},
  //      ]
  //  },


  {path: 'auth/authorized', redirectTo: "auth", pathMatch: 'full'},

  {path: 'auth', component: AuthComponent, pathMatch: 'full'},


  {
    path: 'auth/register',
    component: RegisterComponent,
  },

  {
    path: 'auth/password-reset',
    component: PasswordResetComponent,

  },

  {
    path: 'auth/thanks-for-registration',
    component: ThanksForRegistrationComponent,

  },

  {
    path: 'auth/verifyRegistration',
    children: [
      {path: ':token', component: ValidateRegistrationComponent}
    ]
  },


  {
    path: 'auth/savePassword',
    children: [
      {path: ':token', component: SetNewPasswordComponent}
    ]
  },

  {
    path: 'auth/logged-out',
    component: LoggedOutComponent,

  },
  {
    path: 'privacy-policy',
    component: PrivacyPolicyComponent,
  },
  {
    path: 'terms-of-service',
    component: TermsOfServiceComponent,
  },

  {
    path: 'disclaimers',
    component: DisclaimersComponent,
  },


  {
    path: 'feed',
    component: FeedComponent,
    canActivate: [AuthGuard]
  },

  {
    path: 'chat',
    component: ChatPageComponent,
    canActivate: [AuthGuard]
  },


  {
    path: 'connection',
    component: ConnectionsComponent,
    canActivate: [AuthGuard]
  },

  {
    path: 'university',
    component: GroupPageComponent,
    canActivate: [AuthGuard]
  },

  {
    path: 'group',
    component: GroupPageComponent,
    canActivate: [AuthGuard]
  },


  {
    path: 'profile',
    children: [
      {path: '', component: ViewProfileComponent, canActivate: [AuthGuard]},
      {path: 'edit', component: EditProfileComponent, canActivate: [AuthGuard]}
    ]
  },

  {
    path: 'user',
    children: [
      {path: ':username', component: ViewProfileComponent, canActivate: [AuthGuard]}
    ]
  },

  {
    path: 'developer-mode',
    component: DeveloperModeComponent,
    canActivate: [DeveloperModeGuard]
  },


  /*
   {
       path: 'cars',
       children: [
           { path: '', component: CarAllComponent,  canActivate: [AuthGuard] },
           { path: ':userId', component: CarAllComponent,  canActivate: [AuthGuard] } // canActivate: [NonAuthGuard]
       ]
   },

   */



  {path: '**', component: PageNotFoundComponent},


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
