import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { catchError, Observable } from 'rxjs';
import { HandleErrorService } from 'src/app/common/handle-error/handle-error.service';
import { BaseReponse } from '../../common/base-response.model';
import { DiscountAdd } from '../model/discount-add.model';
import { DiscountRequest } from '../model/discount-request.model';
import { DiscountModel } from '../model/discount.model';

@Injectable({
  providedIn: 'root'
})
export class DiscountService {

  private URL_DISCOUNT = environment.API_LOCAL +'/admin/discounts'
  private URL_ADD_DISCOUNT = environment.API_LOCAL +'/admin/discount'


  constructor(private http: HttpClient,
              private handleErr: HandleErrorService) { }

  
  public findDiscount(input :DiscountRequest ): Observable<BaseReponse<DiscountModel>>{
    return this.http.post<BaseReponse<DiscountModel>>(this.URL_DISCOUNT, input).pipe(
      catchError(this.handleErr.handleError)
    );
  }

  public deleteDisocunt(ids: number[]): Observable<BaseReponse<any>>{
    const url = environment.API_LOCAL +`/admin/discount?ids=${ids.join(',')}`;
    return this.http.delete<BaseReponse<any>>(url).pipe(
      catchError(this.handleErr.handleError)
    );
  }

  public createOrEdit(input: DiscountAdd): Observable<BaseReponse<any>>{
    return this.http.post<BaseReponse<any>>(this.URL_ADD_DISCOUNT, input).pipe(
      catchError(this.handleErr.handleError)
    );
  }
}
