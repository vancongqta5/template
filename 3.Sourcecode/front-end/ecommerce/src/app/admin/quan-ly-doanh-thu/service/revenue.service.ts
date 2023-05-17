import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import * as saveAs from 'file-saver';
import { catchError, Observable } from 'rxjs';
import { HandleErrorService } from 'src/app/common/handle-error/handle-error.service';
import { BaseReponse } from '../../common/base-response.model';
import { AdminRevenueDTO } from '../model/AdminRevenueDTO.model';
import { AdminRevenueReq } from '../model/AdminRevenueReq.model';

@Injectable({
  providedIn: 'root'
})
export class RevenueService {


  private URL_REVENUE = environment.API_LOCAL +'/admin/revenues'
  private URL_EXPORT = environment.API_LOCAL +'/admin/export/revenue'

  constructor(private http: HttpClient,
              private handleErr: HandleErrorService) { }

  
  public findRevenue(request: AdminRevenueReq):Observable<BaseReponse<AdminRevenueDTO>>{
    return this.http.post<BaseReponse<AdminRevenueDTO>>(this.URL_REVENUE, request).pipe(
      catchError(this.handleErr.handleError)
    );
  }

  public exportExcel(fileName: string){
    this.http.get(this.URL_EXPORT, { responseType: 'blob' }).pipe(
      catchError(this.handleErr.handleError)
      ).subscribe(blob => {
      saveAs(blob, fileName);
    });
  }
              
    
}
