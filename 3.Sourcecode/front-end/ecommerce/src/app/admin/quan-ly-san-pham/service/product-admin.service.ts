import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { catchError, Observable } from 'rxjs';
import { HandleErrorService } from 'src/app/common/handle-error/handle-error.service';
import { BaseReponse } from '../../common/base-response.model';
import { ColorModel } from '../../quan-ly-color/model/color.model';
import { CategoryModel } from '../../quan-ly-danh-muc/model/category-response.model';
import { DiscountModel } from '../../quan-ly-discount/model/discount.model';
import { SizeModel } from '../../quan-ly-size/model/size.model';
import { FormAddProductModel } from '../model/form-add-product.model';
import { ProductAdminReQuestModel } from '../model/product-admin-request.model';
import { ProductAdminResponse } from '../model/product-admin-response.model';
import { saveAs } from 'file-saver';
import { ReviewModelRequest } from '../model/review-request.model';
import { ReviewReponseModel } from '../model/review-response.model';
@Injectable({
  providedIn: 'root'
})
export class ProductAdminService {

  private URL_FIND = environment.API_LOCAL+'/admin/product';
  private URL_DELETE = environment.API_LOCAL +'/admin/product';
  private URL_ALL_CATEGORIES = environment.API_LOCAL+'/public/categories';
  private URL_ALL_COLORS = environment.API_LOCAL+'/public/colors';
  private URL_ALL_SIZES = environment.API_LOCAL+'/public/sizes';
  private URL_ALL_DISCOUNTS = environment.API_LOCAL+'/public/discounts';
  private URL_ADD_PRODUCT = environment.API_LOCAL +'/admin/add-product';
  private URL_DETAIL = environment.API_LOCAL +'/admin/product'
  private URL_REVIEW = environment.API_LOCAL +'/admin/reviews';

  private URL_EXPORT = environment.API_LOCAL+'/admin/product/export'
  private URL_IMPORT = environment.API_LOCAL +'/admin/product/import'




  constructor(private http: HttpClient,
              private handleErr: HandleErrorService) { }
  

  public findProduct(request: ProductAdminReQuestModel):Observable<BaseReponse<ProductAdminResponse>>{
    return this.http.post<BaseReponse<ProductAdminResponse>>(this.URL_FIND, request).pipe(
      catchError(this.handleErr.handleError)
    );
  } 

  public deleteProduct(Ids: number[]) : Observable<any>{
    const url = `${this.URL_DELETE}?Ids=${Ids.join(',')}`;
    return this.http.delete<any>(url).pipe(
      catchError(this.handleErr.handleError)
    );
  }

  public getAllCategories(): Observable<BaseReponse<CategoryModel>>{
      return this.http.get<BaseReponse<CategoryModel>>(this.URL_ALL_CATEGORIES).pipe(
        catchError(this.handleErr.handleError)
      )
  }
  public getAllColors(): Observable<BaseReponse<ColorModel>>{
    return this.http.get<BaseReponse<ColorModel>>(this.URL_ALL_COLORS).pipe(
      catchError(this.handleErr.handleError)
    )
  }
  public getAllSizes(): Observable<BaseReponse<SizeModel>>{
    return this.http.get<BaseReponse<SizeModel>>(this.URL_ALL_SIZES).pipe(
      catchError(this.handleErr.handleError)
    )
  }
  public getAllDiscounts(): Observable<BaseReponse<DiscountModel>>{
    return this.http.get<BaseReponse<DiscountModel>>(this.URL_ALL_DISCOUNTS).pipe(
      catchError(this.handleErr.handleError)
    )
  }

  public createOrEditProduct(request: FormAddProductModel):Observable<BaseReponse<any>>{
    return this.http.post<BaseReponse<any>>(this.URL_ADD_PRODUCT, request ).pipe(
      catchError(this.handleErr.handleError)
    )
  }

  public findDetail(id: number):Observable<BaseReponse<FormAddProductModel>>{
    const url = this.URL_DETAIL+`?id=${id}`;
    return this.http.get<BaseReponse<FormAddProductModel>>(url).pipe(
      catchError(this.handleErr.handleError)
    )
  }

  public findReviews(reqest: ReviewModelRequest) : Observable<BaseReponse<ReviewReponseModel>>{
    return this.http.post<BaseReponse<ReviewReponseModel>>(this.URL_REVIEW, reqest).pipe(
      catchError(this.handleErr.handleError)
    )
  }

  public exportExcel(fileName: string){
    this.http.get(this.URL_EXPORT, { responseType: 'blob' }).pipe(
      catchError(this.handleErr.handleError)
      ).subscribe(blob => {
      saveAs(blob, fileName);
    });
  }
  public getApiImport():string {
    return this.URL_IMPORT;
  }
}
