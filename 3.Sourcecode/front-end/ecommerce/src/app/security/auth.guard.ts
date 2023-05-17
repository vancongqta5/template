import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { TokenService } from '../common/token-service/token.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
   
  constructor(private tokenService: TokenService,
              private router: Router){}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if(this.tokenService.getToken()){
      return true;
    }
    else{
      alert('Bạn chưa đăng nhập. Xin vui lòng đăng nhập!')
      this.router.navigate(['/auth/login']);
      return false;
    }
  }
  
}
