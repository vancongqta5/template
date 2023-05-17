import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HandleErrorService } from '../handle-error/handle-error.service';
import { catchError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ExcelService {

  constructor(private http: HttpClient,
              private handleErr: HandleErrorService) { }

  public importExcelFile(file: File, API_URL : string) {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(API_URL, formData).pipe(
        catchError(this.handleErr.handleError)
      );
  }

}