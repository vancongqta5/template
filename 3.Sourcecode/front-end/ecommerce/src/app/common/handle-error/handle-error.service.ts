import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HandleErrorService {

  constructor() { }

  handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // Xử lý lỗi khi client gửi request không thành công
      console.error('Client error:', error.error.message);
    } else {
      // Xử lý lỗi khi server trả về response không thành công
      console.error(`Server error: status=${error.status}, body=${error.error}`);
    }
    // Trả về một observable mới chứa thông tin lỗi
    return throwError('Có lỗi xảy ra, vui lòng thử lại sau.');
  }
}
