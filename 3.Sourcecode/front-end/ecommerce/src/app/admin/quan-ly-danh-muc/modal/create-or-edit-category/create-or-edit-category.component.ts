import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { CategoryAddModel } from '../../model/category-add.model';
import { CategoryModel } from '../../model/category-response.model';
import { CategoryService } from '../../service/category.service';

@Component({
  selector: 'app-create-or-edit-category',
  templateUrl: './create-or-edit-category.component.html',
  styleUrls: ['./create-or-edit-category.component.scss']
})
export class CreateOrEditCategoryComponent implements OnInit{
     category!: CategoryModel;
     AddForm!: FormGroup
     isDetail = false;

     constructor(public ref: DynamicDialogRef,
      public config: DynamicDialogConfig,
      private categoryService: CategoryService,
      private fb: FormBuilder){
     }
    ngOnInit(): void {
      this.category = this.config.data.category;
      this.buildForm();
      if(this.category){
        console.log(this.category)
        this.isDetail = true;
        this.setValue();
        this.AddForm.disable();
      }
    }

    buildForm(){
      this.AddForm = this.fb.group({
        categoryName: [null, Validators.compose([Validators.required])],
        description: [null, Validators.compose([Validators.required])]
      })
    }

    getInput(): CategoryAddModel{
         const input: CategoryAddModel ={
         id: this.category? this.category.id: 0,
         categoryName: this.AddForm.controls['categoryName'].value,
         description: this.AddForm.controls['description'].value
       }
       return input;
    }

    setValue(){
      this.AddForm.controls['categoryName'].setValue(this.category.categoryName);
      this.AddForm.controls['description'].setValue(this.category.description);
    }

    edit(){
       this.AddForm.enable();
       this.isDetail = false;
    }
    

    get f(){
      return this.AddForm.controls;
    }

    onSave(){
      const input = this.getInput();
      this.categoryService.createOrEdit(input).subscribe(res =>{
        location.reload();
      });
      this.ref.close()
    }
    onClose(){
      this.ref.close()
    }
}
