import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { catchError, Observable } from 'rxjs';
import { BaseReponse } from 'src/app/admin/common/base-response.model';
import { HandleErrorService } from 'src/app/common/handle-error/handle-error.service';
import { ProductDetail } from '../model/product-detail.model';
import { ProductReviewReq } from '../model/product-review-req.model';
import { ProductReviewRes } from '../model/product-review-res.model';
import { FormAddReview } from '../product-single.component';

@Injectable({
  providedIn: 'root'
})
export class ProductDetailService {

  private URL_PRODUCT_DETAIL = environment.API_LOCAL +'/shop/product-detail';
  private URL_PRODUCT_REVIEW = environment.API_LOCAL +'/shop/product-detail/review'
  private URL_PRODUCT_REVIEW_ADD = environment.API_LOCAL +'/shop/product-detail/review/add'


  constructor(private http: HttpClient,
              private handleErr: HandleErrorService) {
   }

   // http://localhost:8080/shop/product-detail?id=30
   // http://localhost:8080/shop/product-detail?id=30

   public findProductById(id: number): Observable<BaseReponse<ProductDetail>>{
       const url = this.URL_PRODUCT_DETAIL +`?id=${String(id)}`
       return this.http.get<BaseReponse<ProductDetail>>(url).pipe(
                 catchError(this.handleErr.handleError)
      );
   }
   public findReviewProduct(input: ProductReviewReq): Observable<BaseReponse<ProductReviewRes>>{
         return this.http.post<BaseReponse<ProductReviewRes>>(this.URL_PRODUCT_REVIEW, input).pipe(
          catchError(this.handleErr.handleError)
   );
   }

   public addReviewProduct(input: FormAddReview ): Observable<BaseReponse<any>>{
    return this.http.post<BaseReponse<any>>(this.URL_PRODUCT_REVIEW_ADD, input).pipe(
      catchError(this.handleErr.handleError)
    );
   }
}
