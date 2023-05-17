import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { catchError, Observable } from 'rxjs';
import { HandleErrorService } from 'src/app/common/handle-error/handle-error.service';
import { BaseReponse } from '../../common/base-response.model';
import { CategoryAddModel } from '../model/category-add.model';
import { CategoryRequestModel } from '../model/category-request.model';
import { CategoryModel } from '../model/category-response.model';


@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private URL_CATEGORY = environment.API_LOCAL +'/admin/categories'
  private URL_ADD_CATEGORY = environment.API_LOCAL +'/admin/category'


  constructor(private http: HttpClient,
               private handleErr: HandleErrorService) { }

  public findCategory(request: CategoryRequestModel):Observable<BaseReponse<CategoryModel>>{
    return this.http.post<BaseReponse<CategoryModel>>(this.URL_CATEGORY, request).pipe(
      catchError(this.handleErr.handleError)
    );
  }

  public deleteCategory(ids: number[]): Observable<BaseReponse<any>>{
    const url = environment.API_LOCAL +`/admin/category?ids=${ids.join(',')}`
    return this.http.delete<BaseReponse<any>>(url).pipe(
      catchError(this.handleErr.handleError)
    );
  }

  public createOrEdit(input: CategoryAddModel): Observable<any>{
    return this.http.post(this.URL_ADD_CATEGORY, input).pipe(
      catchError(this.handleErr.handleError)
    );
  }


}
