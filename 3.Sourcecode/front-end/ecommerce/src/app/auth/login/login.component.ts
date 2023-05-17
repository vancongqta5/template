import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { Router } from '@angular/router';
import { DataService } from 'src/app/common/data-service/data.service';
import { TokenService } from 'src/app/common/token-service/token.service';
import { SignInForm } from './model/signin-form.model';
import { LoginService } from './service/login.service';
import  { AvatarModel} from '../../shared/header/model/avt.model'

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  formLogin!: FormGroup;
  showMessage = false;
  dangerMessage =''
  data: any

  constructor(private fb: FormBuilder,
              private loginService: LoginService,
              private tokenService: TokenService,
              private dataService: DataService,
              private router: Router) { }

  ngOnInit(): void {
    this.buildForm();
  }

  buildForm(){
    this.formLogin = this.fb.group({
      username: [null, Validators.compose([Validators.required])],
      password: [null, Validators.compose([Validators.required,  Validators.minLength(6)])]
    });
  }

  getInput(): SignInForm{
    const input: SignInForm ={
      username: this.formLogin.controls['username'].value,
      password: this.formLogin.controls['password'].value
    }
    return input;
  }

  get f(){
    return this.formLogin.controls;
  }

  login(){
    const input = this.getInput();
    if(input != null){
      this.loginService.login(input).subscribe(res =>{
        if(res.message === 'locked'){
          this.showMessage = true;
          this.dangerMessage = 'Bạn đã bị khóa tài khoản. Vui lòng liên hệ với chủ shop để biết thêm chi tiết'
        }
        else if(res.message === 'fail'){
          this.showMessage = true;
          this.dangerMessage ='Tên đăng nhập hoặc mật khẩu không đúng'
        }
        else {
          this.setValueLocalStorange(res);
          // Truyền dữ liệu cho header-component để thay đổi avatar
          //  this.data = this.transferData(res);
          //  this.dataService.setData(this.data);

          this.router.navigate([''])
        }
      })
    }
  }

  setValueLocalStorange(res: any){
    this.tokenService.setToken(res.token);
    this.tokenService.setRefreshToken(res.refreshToken);
    this.tokenService.setName(res.name);
    this.tokenService.setAvatar(res.avatar);
    this.tokenService.setRoles(res.roles);
    this.tokenService.setId(String(res.id))
  }

  transferData(res: any){
    const data: AvatarModel ={
      name: this.tokenService.getName(),
      avatar: this.tokenService.getAvatar()
    }
    return data;
  }

}
