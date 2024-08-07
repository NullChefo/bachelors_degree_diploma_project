import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthComponent } from './component/auth/auth.component';
import { WebAppHomeComponent } from './component/web-app-home/web-app-home.component';
import { HomeComponent } from './pages/home/home.component';

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

  { path: 'home', component: WebAppHomeComponent, pathMatch: 'full' },



  { path: 'auth/authorized', redirectTo: "auth", pathMatch: 'full' },

  { path: 'auth', component: AuthComponent, pathMatch: 'full' },




];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
