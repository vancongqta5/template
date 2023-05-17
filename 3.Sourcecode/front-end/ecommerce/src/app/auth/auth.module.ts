import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
import { AuthComponent } from './auth.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import {SharedModule} from "../shared/shared.module";
import {ReactiveFormsModule} from "@angular/forms";


@NgModule({
  declarations: [
    AuthComponent,
    LoginComponent,
    SignupComponent
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    SharedModule,
    ReactiveFormsModule,
  ]
})
export class AuthModule { }
