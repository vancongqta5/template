import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
  HttpResponse
} from '@angular/common/http';
import { catchError, map, Observable, switchMap, throwError } from 'rxjs';
import { TokenService } from '../token-service/token.service';
import { AuthService } from '../auth-service/auth.service';
import { Router } from '@angular/router';
import { DialogService } from 'primeng/dynamicdialog';
import { PopupConfirmComponent } from 'src/app/shared/popup-confirm/popup-confirm.component';
@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private tokenService: TokenService,
              private authService: AuthService,
              private dialogService: DialogService,
              private router: Router) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    console.log('Đã đính token(JWT) vào request!') 
    let authRequest = request;
    const token = this.tokenService.getToken();
    if(token != null){
      authRequest = request.clone(
        {
          setHeaders:{
            Authorization:  `Bearer ${token}`
          }
        })
    }
    return next.handle(authRequest).pipe(
      map((event: HttpEvent<any>) => {
        if (event instanceof HttpResponse) {
          // Trích xuất giá trị từ response
          const httpCode = event.body.status;
          if(event.status === 401 || httpCode === 401) this.redirectToLogin(); // token het han
        }
        return event;
      }),
      catchError((error: HttpErrorResponse) => {
        // Xử lý lỗi
        console.log(`HTTP code: ${error.status}, Message: ${error.error.message}, Data: ${error.error.data}`);
        console.log( error.error.error)
        if(error.error.error === 'Unauthorized') {
          this.redirectToLogin(); // token het han
        }
        return throwError(error);
      })
    );
  }

  redirectToLogin() {
    // Chuyển về trang login
    const confirm = this.dialogService.open(PopupConfirmComponent, {
      showHeader: false,
      baseZIndex: 10000,
      data: {
        title: 'Bạn đã hết hạn đăng nhập. Vui lòng đăng nhập lại',
        content: '',
        status: 0
      }
    });

    confirm.onClose.subscribe(res => {
      this.router.navigate(['/auth/login'])
    })
  }
}

