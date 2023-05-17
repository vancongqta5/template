import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DynamicDialogRef, DynamicDialogConfig } from 'primeng/dynamicdialog';
import { ColorAddModel } from '../../model/color-add.model';
import { ColorModel } from '../../model/color.model';
import { ColorService } from '../../service/color.service';

@Component({
  selector: 'app-create-or-edit-color',
  templateUrl: './create-or-edit-color.component.html',
  styleUrls: ['./create-or-edit-color.component.scss']
})
export class CreateOrEditColorComponent implements OnInit {
  AddForm!: FormGroup
  isDetail = false;
  color!: ColorModel

  constructor(public ref: DynamicDialogRef,
    public config: DynamicDialogConfig,
    private colorService: ColorService,
    private fb: FormBuilder){
   }
  ngOnInit(): void {
    this.buildForm();
    this.color = this.config.data.color;
    if(this.color){
      this.isDetail = true;
      this.setValue();
      this.AddForm.disable();
    }
  }


   buildForm(){
    this.AddForm = this.fb.group({
      colorName: [null, Validators.compose([Validators.required])],
      colorCode: [null, Validators.compose([Validators.required])]
    })
  }

  setValue(){
     this.AddForm.controls['colorName'].setValue(this.color.colorName);
     this.AddForm.controls['colorCode'].setValue(this.color.colorCode);
  }

  getInput(): ColorAddModel{
       const input : ColorAddModel ={
         id: this.color? this.color.id: 0,
         colorName: this.AddForm.controls['colorName'].value,
         colorCode: this.AddForm.controls['colorCode'].value,
       }
       return input;
  }

  get f(){
    return this.AddForm.controls;
  }

  onSave(){
    const input = this.getInput();
    this.colorService.createOrEditColor(input).subscribe(res =>{
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
