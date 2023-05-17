import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import * as saveAs from 'file-saver';
import { catchError, Observable } from 'rxjs';
import { HandleErrorService } from 'src/app/common/handle-error/handle-error.service';
import { BaseReponse } from '../../common/base-response.model';
import { CustomerRequest } from '../model/customer-request.model';
import { CustomerResponse } from '../model/customer-response.model';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  
  private URL_CUSTOMER = environment.API_LOCAL +'/admin/customer'
  private URL_CHANGE_STATUS = environment.API_LOCAL +"/admin/customer"
  private URL_EXPORT_CUSTOMER = environment.API_LOCAL + '/admin/customer/export'

  constructor(private http: HttpClient,
    private handleErr: HandleErrorService) { }
  
  public findCustomer(request: CustomerRequest): Observable<BaseReponse<CustomerResponse>>{
    return this.http.post<BaseReponse<CustomerResponse>>(this.URL_CUSTOMER, request).pipe(
      catchError(this.handleErr.handleError)
    );
  }

  public changeStatus(status: number, ids: number[]):Observable<BaseReponse<any>> {
    const url = `${this.URL_CHANGE_STATUS}?status=${status}&ids=${ids.join(',')}`;
    return this.http.get<BaseReponse<any>>(url).pipe(
      catchError(this.handleErr.handleError)
    );
  }
  public exportExcel(fileName: string, input: CustomerRequest){
    this.http.post(this.URL_EXPORT_CUSTOMER, input, { responseType: 'blob' }).pipe(
      catchError(this.handleErr.handleError)
      ).subscribe(blob => {
      saveAs(blob, fileName);
    });
  }

}
