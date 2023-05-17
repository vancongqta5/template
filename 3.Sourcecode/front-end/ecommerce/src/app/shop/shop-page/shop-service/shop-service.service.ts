import { Injectable } from '@angular/core';
import {environment} from "../../../../../environments/environment";
import {HttpClient, HttpEvent, HttpHeaders, HttpResponse} from "@angular/common/http";
import {PageRequest} from "../../../admin/common/page-request.model";
import {Observable} from "rxjs";
import {BaseReponse} from "../../../admin/common/base-response.model";
import {ProductsResponse} from "../shop-model/shop-response.model";
import {ProductRequest} from "../shop-model/shop-request.model";
import {HandleErrorService} from "../../../common/handle-error/handle-error.service";
import {catchError} from "rxjs/operators";
import {ProductAdminReQuestModel} from "../../../admin/quan-ly-san-pham/model/product-admin-request.model";
import {ProductAdminResponse} from "../../../admin/quan-ly-san-pham/model/product-admin-response.model";

@Injectable({
  providedIn: 'root'
})
export class ShopServiceService {

  private URL_GET = environment.API_LOCAL+ '/shop-page'

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    }),
    'Access-Control-Allow-Origin': 'http://localhost:4200',
    'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE,PATCH,OPTIONS'
  }

  constructor(private http:HttpClient,
              private handleErr: HandleErrorService) { }

  // public findProductForShop(request: PageRequest): Observable<BaseReponse<ProductsResponse>>{
  //   return this.http.post<BaseReponse<ProductsResponse>>(this.URL_GET,request);
  // }

  public findProductForShop(request: ProductRequest):Observable<BaseReponse<ProductsResponse>>{
    return this.http.post<BaseReponse<ProductsResponse>>(this.URL_GET, request).pipe(
      catchError(this.handleErr.handleError)
    );
  }

}
