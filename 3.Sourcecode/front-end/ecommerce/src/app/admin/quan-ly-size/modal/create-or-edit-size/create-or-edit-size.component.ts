import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { DynamicDialogRef, DynamicDialogConfig } from 'primeng/dynamicdialog';
import { SizeModel } from '../../model/size.model';
import {SizeAdminAdd} from '../../model/size-admin-add.model';
import { SizeService } from '../../service/size.service';

@Component({
  selector: 'app-create-or-edit-size',
  templateUrl: './create-or-edit-size.component.html',
  styleUrls: ['./create-or-edit-size.component.scss']
})
export class CreateOrEditSizeComponent implements OnInit {
  AddForm!: FormGroup
  isDetail = false;
  size!: SizeModel

  constructor(public ref: DynamicDialogRef,
    public config: DynamicDialogConfig,
    private sizeService: SizeService,
    private fb: FormBuilder){
   }
  ngOnInit(): void {
    this.buildForm();
    this.size = this.config.data.size;
    if(this.size){
      this.isDetail = true;
      this.setValue();
      this.AddForm.disable();
    }
  }


   buildForm(){
    this.AddForm = this.fb.group({
      sizeName: [null, Validators.compose([Validators.required])],
      sizeCode: [null, Validators.compose([Validators.required])]
    })
  }

  setValue(){
     this.AddForm.controls['sizeName'].setValue(this.size.sizeName);
     this.AddForm.controls['sizeCode'].setValue(this.size.sizeCode);
  }

  getInput(): SizeAdminAdd{
       const input : SizeAdminAdd ={
         id: this.size? this.size.id: 0,
         sizeName: this.AddForm.controls['sizeName'].value,
         sizeCode: this.AddForm.controls['sizeCode'].value,
       }
       return input;
  }

  get f(){
    return this.AddForm.controls;
  }

  onSave(){
    const input = this.getInput();
    this.sizeService.createOrEdit(input).subscribe(res =>{
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
