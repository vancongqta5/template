import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { catchError, Observable } from 'rxjs';
import { HandleErrorService } from 'src/app/common/handle-error/handle-error.service';
import { SignInForm } from '../model/signin-form.model';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private  API_LOGIN = environment.API_LOCAL +'/auth/login';

  constructor(private http: HttpClient,
              private handleErr: HandleErrorService) { }

  public login(signInForm: SignInForm): Observable<any>{
    return this.http.post(this.API_LOGIN, signInForm)
    .pipe(
      catchError(this.handleErr.handleError)
    );
  }

}
