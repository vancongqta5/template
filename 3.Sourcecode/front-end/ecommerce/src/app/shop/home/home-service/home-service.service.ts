import { Injectable } from '@angular/core';
import {environment} from "../../../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {HandleErrorService} from "../../../common/handle-error/handle-error.service";
import {PageRequest} from "../../../admin/common/page-request.model";
import {catchError, Observable} from "rxjs";
import {BaseReponse} from "../../../admin/common/base-response.model";
import {Products} from "../home-model/home-response.model";

@Injectable({
  providedIn: 'root'
})
export class HomeServiceService {

  private URL_FIND = environment.API_LOCAL+'/public/home'

  constructor(private http: HttpClient,
              private handleErr: HandleErrorService) { }
  public findProduct(request: PageRequest):Observable<BaseReponse<Products>>{

    // @ts-ignore
    return this.http.post<BaseReponse<Products>>(this.URL_FIND, request).pipe(
      catchError(this.handleErr.handleError)
    );
  }
}
