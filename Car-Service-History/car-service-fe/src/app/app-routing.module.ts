import {NgModule} from '@angular/core';
import {Route, RouterModule} from '@angular/router';
import { CarAddComponent } from './components/car/car-add/car-add.component';


import { AuthGuard } from './guards/auth.guard';



import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { PageNotFoundComponent } from './pages/auth/page-not-found/page-not-found.component';
import { SetNewPasswordComponent } from './components/auth/set-new-password/set-new-password.component';
import { ThanksForRegistrationComponent } from './components/auth/thanks-for-registration/thanks-for-registration.component';
import { ValidateRegistrationComponent } from './components/auth/validate-registration/validate-registration.component';
import { CarAllComponent } from './components/car/car-all/car-all.component';
import { PasswordResetComponent } from './pages/auth/password-reset/password-reset.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { EventAddComponent } from './components/event/event-add/event-add.component';
import { EventAllComponent } from './components/event/event-all/event-all.component';




const routes: Route[] = [
    /* 
    {
        path: 'auth',
        loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule),
        canLoad: [NonAuthGuard]
    },
    {
        path: 'home',
        component: HomeComponent,
        canLoad: [NonAuthGuard]
    },
    {
        path: 'main',
        loadChildren: () => import('./jobs/jobs.module').then(m => m.JobsModule),
        canLoad: [AuthGuard]
    },
*/

{
    path: '',
    pathMatch: 'full',
    component: HomeComponent
},

{// TODO load guard
    path: 'user/login',
 //   canActivate: [NonAuthGuard],
    children: [

        { path: '', component: LoginComponent  },  // canActivate: [NonAuthGuard]
        { path: ':error', component: LoginComponent  } // canActivate: [NonAuthGuard]

    ]
},
{// TODO load guard
    path: 'user/register',
    component: RegisterComponent,
//   canLoad: [NonAuthGuard]
},

{
    path: 'user/password-reset',
    component: PasswordResetComponent,
  
},


{
    path: 'user/thanks-for-registration',
    component: ThanksForRegistrationComponent,
  
},

{
    path: 'user/thanks-for-registration',
    component: ThanksForRegistrationComponent,
  
},

{
    path: 'user/verifyRegistration',
    children: [
        { path: ':token', component: ValidateRegistrationComponent}
    ]
},


{
    path: 'user/savePassword',
    children: [
        { path: ':token', component: SetNewPasswordComponent}
    ]
},

{
    path: 'cars',
    children: [
        { path: '', component: CarAllComponent,  canActivate: [AuthGuard] },
        { path: ':userId', component: CarAllComponent,  canActivate: [AuthGuard] } // canActivate: [NonAuthGuard]
    ]
},


{
    path: 'car/add',
    children: [
        { path: '', component: CarAddComponent,  canActivate: [AuthGuard] }, 
        { path: ':carId', component: CarAddComponent,  canActivate: [AuthGuard] } // canActivate: [NonAuthGuard]
    ]
},





{
    path: 'event',
    children: [
        { path: '', component: EventAddComponent,  canActivate: [AuthGuard] }, 
        { path: ':carId', component: EventAddComponent,  canActivate: [AuthGuard] }, // canActivate: [NonAuthGuard]
        { path: 'edit/:eventId', component: EventAddComponent,  canActivate: [AuthGuard] } // canActivate: [NonAuthGuard]
    ]
},


{
    path: 'events',
    children: [
        { path: '', component: EventAllComponent,  canActivate: [AuthGuard] }, 
        { path: ':carId', component: EventAllComponent,  canActivate: [AuthGuard] }, // canActivate: [NonAuthGuard]
    ]
},


{ path: '**', component: PageNotFoundComponent },


];

@NgModule({
    imports: [
        RouterModule.forRoot(routes)
    ],
    exports: [
        RouterModule
    ]
})

export class AppRoutingModule {
}
