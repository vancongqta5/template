
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ErrorComponent } from '../shop/error/error.component';

import { AuthComponent } from './auth.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';

const routes: Routes = [{ path: '', component: AuthComponent },
  {path: 'login', component: LoginComponent},
  {path: 'sign-up', component: SignupComponent},
  {path: '**', component: ErrorComponent}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
