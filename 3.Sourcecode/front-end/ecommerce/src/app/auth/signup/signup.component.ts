import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { forkJoin } from 'rxjs';
import { UpLoadFileService } from 'src/app/common/upload-file/up-load-file.service';
import { SignUpService } from './service/sign-up.service';
import { SignUpForm } from './model/signup-form.model';
import { PopupConfirmComponent } from 'src/app/shared/popup-confirm/popup-confirm.component';
import { DialogService } from 'primeng/dynamicdialog';
import { Router } from '@angular/router';
@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {
  formRegister!: FormGroup;
  avatarDefaut = 'https://thumbs.dreamstime.com/b/default-avatar-profile-icon-vector-social-media-user-portrait-176256935.jpg';
  isLoading = false;
  gender =[
    {code: 1, name: 'Nam'},
    {code: 2, name: 'Nữ'}
  ]
  dangerMessage = ''
  showMessage = false;

  constructor(private fb: FormBuilder,
              private signUpService: SignUpService,
              private dialogService: DialogService,
              private uploadFileService: UpLoadFileService,
              private router: Router
              ) { }
  ngOnInit(): void {
    this.buildForm();
  }
  
  buildForm(){
    this.formRegister = this.fb.group({
      username: [null, Validators.required],
      email: [null, [Validators.required, Validators.email]],
      password: [null, [Validators.required, Validators.minLength(6)]],
      fullName:[null, Validators.required],
      address:[null, Validators.required],
      gender:[null, Validators.required],
      phoneNumber:[null, Validators.required]
    });
  }

  get f(){
    return this.formRegister.controls;
  }
  
  chooseFile(event: any){
    this.isLoading = true;
    const selectedFile = event.files[0]
    this.uploadFileService.uploadFileAndGetDownloadUrl(selectedFile, "avatar").subscribe(
      url => {
        this.avatarDefaut = url;
        this.isLoading = false;
      },
      error => console.error(error)
    );
  }

  getInput(): SignUpForm{
        const input: SignUpForm ={
          userName: this.formRegister.controls['username'].value,
          password: this.formRegister.controls['password'].value,
          fullName: this.formRegister.controls['fullName'].value,
          email: this.formRegister.controls['email'].value,
          phoneNumber: this.formRegister.controls['phoneNumber'].value,
          gender: this.formRegister.controls['gender'].value,
          address: this.formRegister.controls['address'].value,
          avatar: this.avatarDefaut,
        }
        return input;
  }

  signup(){
    const input = this.getInput()
    if(input != null){
      this.signUpService.signUp(input).subscribe(res =>{
        if(res.message === 'success'){
           this.dangerMessage = 'Bạn đã đăng kí tài khoản thành công';

           const confirm = this.dialogService.open(PopupConfirmComponent, {
            showHeader: false,
            baseZIndex: 10000,
            data: {
              title: 'Bạn đã đăng kí tài khoản thành công',
              content: '',
              status: 0 // chế độ mở popup xác nhận thành công
            }
          });
            confirm.onClose.subscribe(res =>{
            console.log(res)
            this.router.navigate(['/auth/login']);
            })
        }
        else if(res.message === 'nouser'){
           this.dangerMessage ='Tên đăng nhập đã tồn tại. Vui lòng nhập tên đăng nhập khác!'
           this.showMessage = true;
        }
        else{
          this.dangerMessage ='Email đã tồn tại. Vui lòng nhập email khác!'
          this.showMessage = true;
        }
      })
    }
  }
  
  


}
