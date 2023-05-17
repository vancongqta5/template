import { Injectable } from '@angular/core';
import {environment} from "../../../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {HandleErrorService} from "../../../common/handle-error/handle-error.service";
import {ProductRequest} from "../../shop-page/shop-model/shop-request.model";
import {Observable} from "rxjs";
import {BaseReponse} from "../../../admin/common/base-response.model";
import {ProductsResponse} from "../../shop-page/shop-model/shop-response.model";
import {catchError} from "rxjs/operators";
import {CartResponse} from "../cart-model/cart-response.model";
import {CartRequest} from "../cart-model/cart-request.model";
import { PageRequest } from 'src/app/admin/common/page-request.model';

@Injectable({
  providedIn: 'root'
})
export class ShopCartService {

  private URL_GET = environment.API_LOCAL+ '/shop/cart/products';

  private URL_MONEY = environment.API_LOCAL +'/shop/cart/product-detail';

  private URL_DELETE = environment.API_LOCAL +'/shop/cart/delete';

  private URL_CRETAE = environment.API_LOCAL +'/shop/cart/create';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    }),
    'Access-Control-Allow-Origin': 'http://localhost:4200',
    'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE,PATCH,OPTIONS'
  }

  constructor(private http:HttpClient,
              private handleErr: HandleErrorService) { }



  public findProductForCart(request: PageRequest):Observable<BaseReponse<CartResponse>>{
    return this.http.post<BaseReponse<CartResponse>>(this.URL_GET, request).pipe(
      catchError(this.handleErr.handleError)
    );
  }

  public findMoney(id: number, count: number): Observable<any>{
       const url = this.URL_MONEY +`?id=${id}&count=${count}`;
       return this.http.get(url).pipe(
        catchError(this.handleErr.handleError)
      );
  }

  public delete(id: number): Observable<any>{
    const url = this.URL_DELETE + `?id=${id}`
    return this.http.delete(url).pipe(
      catchError(this.handleErr.handleError)
    );
  }

  public create(id: number): Observable<any>{
    const url = this.URL_CRETAE +`?id=${id}`
    return this.http.get(url).pipe(
      catchError(this.handleErr.handleError)
    );
  }
}
