import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import {  Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
// import {Observable} from 'rxjs/Rx';
import { HandleErrorService } from 'src/app/common/handle-error/handle-error.service';
import { SignUpForm } from '../model/signup-form.model';

@Injectable({
  providedIn: 'root'
})
export class SignUpService  {
  
  private  API_SIGNUP = environment.API_LOCAL +'/auth/signup';

  constructor(private http: HttpClient,
              private handleErr: HandleErrorService) { }

  public signUp(signUpForm: SignUpForm): Observable<any>{
    return this.http.post(this.API_SIGNUP, signUpForm)
    .pipe(
      catchError(this.handleErr.handleError)
    );
  }

}
