import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { catchError, Observable } from 'rxjs';
import { HandleErrorService } from 'src/app/common/handle-error/handle-error.service';
import { BaseReponse } from '../../common/base-response.model';
import { ColorAddModel } from '../model/color-add.model';
import { ColorRequestModel } from '../model/color-request.model';
import { ColorModel } from '../model/color.model';

@Injectable({
  providedIn: 'root'
})
export class ColorService {

  private URL_COLOR = environment.API_LOCAL +'/admin/colors'
  private URL_ADD_COLOR = environment.API_LOCAL +'/admin/color'


  constructor(private http: HttpClient,
    private handleErr: HandleErrorService) { }

    public findColor(input: ColorRequestModel): Observable<BaseReponse<ColorModel>>{
      return this.http.post<BaseReponse<ColorModel>>(this.URL_COLOR, input).pipe(
        catchError(this.handleErr.handleError)
      );
    }

    public deleteColor(ids: number[]): Observable<BaseReponse<any>>{
      const url = environment.API_LOCAL +`/admin/color?ids=${ids.join(',')}`;
      return this.http.delete<BaseReponse<any>>(url).pipe(
        catchError(this.handleErr.handleError)
      );
    }

    public createOrEditColor(input: ColorAddModel): Observable<BaseReponse<any>>{
      return this.http.post<BaseReponse<any>>(this.URL_ADD_COLOR, input).pipe(
        catchError(this.handleErr.handleError)
      );
    }
}
