import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'environments/environment';
import { DataService } from 'src/app/common/data-service/data.service';
import { TokenService } from 'src/app/common/token-service/token.service';
import { AvatarModel } from './model/avt.model';
import { LogoutService } from './service/logout.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
      checkedLogin = false; 
      data!: AvatarModel
      name : any
      avatar : any
      adminType = false;

      constructor(private tokenService: TokenService,
        private logoutService: LogoutService,
        private dataService: DataService,
        private router: Router
       ){}

      ngOnInit(): void {
        this.checkedLogin = localStorage.getItem(environment.TOKEN_KEY)? true: false;
        this.checkLogin();
       this.checkUserType();

      }

      checkUserType(){
        if(JSON.stringify(this.tokenService.getRoles()) === JSON.stringify(["ADMIN"]) ||
          JSON.stringify(this.tokenService.getRoles()) === JSON.stringify(["ADMIN", "USER"])){
          this.adminType =  true;
        }
        else{
          this.adminType = false;
        }
      }

      checkLogin(){
        if(localStorage.getItem(environment.TOKEN_KEY)){
          this.name = localStorage.getItem(environment.NAME_KEY);
          this.avatar = localStorage.getItem(environment.AVATAR_KEY);
        }
      }

      logout(){
         this.logoutService.logout().subscribe(res =>{
          this.removeDataStorage();
          this.checkedLogin = false;
          this.name ='';
          this.avatar ='';
          this.router.navigate([''])
          // location.reload();
         })
      }

      removeDataStorage(){
        if(this.tokenService.getToken()){
           this.tokenService.removeDataLocalStorage();
        }
      }

      
}
