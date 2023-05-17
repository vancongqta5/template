import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { TokenService } from '../common/token-service/token.service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {

  constructor(private tokenService: TokenService,
              private router: Router){}
    
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      if(this.tokenService.getToken()){
        if(JSON.stringify(this.tokenService.getRoles()) === JSON.stringify(["ADMIN"]) ||
          JSON.stringify(this.tokenService.getRoles()) === JSON.stringify(["ADMIN", "USER"])){
          return true;
        }
        else{
          alert("Bạn không có quyền của admib");
          this.router.navigate(['']);
          return false;
        }
      }
      else{
        alert("Bạn chưa đăng nhập hoặc chưa đăng kí");
        this.router.navigate(['auth/login']);
        return false;
      }

  }
  
}
