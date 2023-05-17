import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { DynamicDialogRef, DynamicDialogConfig } from 'primeng/dynamicdialog';
import { DiscountAdd } from '../../model/discount-add.model';
import { DiscountModel } from '../../model/discount.model';
import { DiscountService } from '../../service/discount.service';

@Component({
  selector: 'app-create-or-edit-discount',
  templateUrl: './create-or-edit-discount.component.html',
  styleUrls: ['./create-or-edit-discount.component.scss']
})
export class CreateOrEditDiscountComponent {
  AddForm!: FormGroup
  isDetail = false;
  discount!: DiscountModel

  constructor(public ref: DynamicDialogRef,
    public config: DynamicDialogConfig,
    private discountService: DiscountService,
    private fb: FormBuilder){
   }
  ngOnInit(): void {
    this.buildForm();
    this.discount = this.config.data.discount;
    if(this.discount){
      this.isDetail = true;
      this.setValue();
      this.AddForm.disable();
    }
  }


   buildForm(){
    this.AddForm = this.fb.group({
      discountName: [null, Validators.compose([Validators.required])],
      discountPercent: [null, Validators.compose([Validators.required])]
    })
  }

  setValue(){
     this.AddForm.controls['discountName'].setValue(this.discount.discountName);
     this.AddForm.controls['discountPercent'].setValue(this.discount.discountPercent);
  }

  getInput(): DiscountAdd{
       const input : DiscountAdd ={
         id: this.discount? this.discount.id: 0,
         discountName: this.AddForm.controls['discountName'].value,
         discountPercent: this.AddForm.controls['discountPercent'].value,
       }
       return input;
  }

  get f(){
    return this.AddForm.controls;
  }

  onSave(){
    const input = this.getInput();
    this.discountService.createOrEdit(input).subscribe(res =>{
      location.reload();
    });
    this.ref.close()
  }
  onClose(){
    this.ref.close()
  }
  edit(){
      this.isDetail = false;
      this.AddForm.enable();
  }
}
