import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { catchError, Observable } from 'rxjs';
import { HandleErrorService } from 'src/app/common/handle-error/handle-error.service';
import { BaseReponse } from '../../common/base-response.model';
import { SizeAdminAdd } from '../model/size-admin-add.model';
import { SizeAdminRequest } from '../model/size-admin-resquest.model';
import { SizeModel } from '../model/size.model';

@Injectable({
  providedIn: 'root'
})
export class SizeService {

  private URL_SIZE = environment.API_LOCAL +'/admin/sizes'
  private URL_ADD = environment.API_LOCAL +'/admin/size'

  constructor(private http: HttpClient,
    private handleErr: HandleErrorService) { }
  

  public findSize(input: SizeAdminRequest): Observable<BaseReponse<SizeModel>>{
    return this.http.post<BaseReponse<SizeModel>>(this.URL_SIZE, input).pipe(
      catchError(this.handleErr.handleError)
    );
  }

  public deleteSize(ids: number[]): Observable<BaseReponse<any>>{
    const url = environment.API_LOCAL+`/admin/size?ids=${ids.join(',')}`
    return this.http.delete<BaseReponse<any>>(url).pipe(
      catchError(this.handleErr.handleError)
    );
  }

  public createOrEdit(input: SizeAdminAdd): Observable<BaseReponse<any>>{
    return this.http.post<BaseReponse<any>>(this.URL_ADD, input).pipe(
      catchError(this.handleErr.handleError)
    );
  }


}
