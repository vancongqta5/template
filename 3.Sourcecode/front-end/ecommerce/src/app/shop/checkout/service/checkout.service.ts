import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { catchError, Observable } from 'rxjs';
import { BaseReponse } from 'src/app/admin/common/base-response.model';
import { HandleErrorService } from 'src/app/common/handle-error/handle-error.service';
import { CheckoutRequestModel } from '../model/checkout-request.model';
import { CheckOutResponseModel } from '../model/checkout-response.model';
import { RevenueRequestModel } from '../model/revenue-request.model';

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {

  private URL_CHECKOUT = environment.API_LOCAL +'/checkout/products';
  private URL_REVENUE = environment.API_LOCAL +'/checkout/revenue';

  constructor(private http: HttpClient,
    private handleErr: HandleErrorService) { }


    public getAllproductCheckOut(request: CheckoutRequestModel ): Observable<BaseReponse<CheckOutResponseModel>>{
      const headers = new HttpHeaders({
        'Content-Type': 'application/json'
      });
      return this.http.post<BaseReponse<CheckOutResponseModel>>(this.URL_CHECKOUT, JSON.stringify(request),  { headers }).pipe(
        catchError(this.handleErr.handleError)
      );
    }

    public payment(request: CheckoutRequestModel): Observable<BaseReponse<any>>{
      const headers = new HttpHeaders({
        'Content-Type': 'application/json'
      });
      return  this.http.post<BaseReponse<any>>(this.URL_REVENUE, JSON.stringify(request),  { headers }).pipe(
        catchError(this.handleErr.handleError)
      );
    }
}
