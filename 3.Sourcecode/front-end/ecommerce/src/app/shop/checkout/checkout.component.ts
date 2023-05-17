import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { DialogService } from 'primeng/dynamicdialog';
import { flatMap } from 'rxjs';
import { DataService } from 'src/app/common/common/data.service';
import { ProductTransferCard } from 'src/app/common/utils/product-transfer-card.model';
import { PopupConfirmComponent } from 'src/app/shared/popup-confirm/popup-confirm.component';
import { CheckoutRequestModel } from './model/checkout-request.model';
import { CheckOutModel } from './model/checkout.model';
import { CheckoutService } from './service/checkout.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss']
})
export class CheckoutComponent implements OnInit {
   products!: ProductTransferCard[]
   Form!: FormGroup
   data: any
   param! : CheckOutModel[]
   totalMany: any

   constructor(private dataService: DataService,
              private dialogService: DialogService,
              private messageService: MessageService,
              private checkoutService: CheckoutService,
              private fb: FormBuilder){
   }


  ngOnInit(): void {
    this.products = this.dataService.getProducts();
    console.log(this.products)
    if(this.products){
          this.param = this.products;
          this.param = this.param.flat();
          this.getData();
      }
    this.buildForm();
  }

    buildForm() {
      this.Form = this.fb.group({
        firstName: ['', Validators.required],
        lastName: ['', Validators.required],
        companyName: ['', Validators.required],
        country: ['', Validators.required],
        street :['', Validators.required],
        apartment: ['', Validators.required],
        district: ['', Validators.required],
        phone: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]]
      });
    }


  getFormControl(name: string) {
    return this.Form.get(name);
  }

  get form(){
    return this.Form;
  }

  getErrorMessage(name: string) {
    const control = this.getFormControl(name);
    if (control && control.errors) {
      if (control.errors['required']) {
        return 'This field is required.';
      } else if (control.errors['email']) {
        return 'Invalid email format.';
      }
    }
    return '';
  }

  getData(){
    const input : CheckoutRequestModel={
      request: this.param
    }
    if(input){
        this.checkoutService.getAllproductCheckOut(input).subscribe(res =>{
          this.data = res.data.products;
          this.totalMany = res.data.totalMany;
          console.log(this.data)
        })
 
    }
  }
  payment(){
    const confirm = this.dialogService.open(PopupConfirmComponent, {
      showHeader: false,
      baseZIndex: 10000,
      data: {
        title: 'Cảm ơn ! Bạn đã đặt hàng thành công! Bạn sẽ sớm nhận được hàng <3',
        content: '',
        status: 0
      }
    });

    confirm.onClose.subscribe(res => {
          const input : CheckoutRequestModel={
            request: this.param
          }
          this.checkoutService.payment(input).subscribe(res =>{
            console.log(res)
          })
        
    })

  }

}
