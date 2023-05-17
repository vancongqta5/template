import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from '@firebase/util';
import { environment } from 'environments/environment';
import { catchError } from 'rxjs';
import { HandleErrorService } from 'src/app/common/handle-error/handle-error.service';

@Injectable({
  providedIn: 'root'
})
export class LogoutService {

  private URL_LOGOUT = environment.API_LOCAL +'/auth/logout';

  constructor(private http: HttpClient,
    private handleErr: HandleErrorService) { }


  public logout(): Observable<any>{
    return this.http.get(this.URL_LOGOUT)
      .pipe(
        catchError(this.handleErr.handleError)
      ) as unknown as Observable<any>;
  }
}
