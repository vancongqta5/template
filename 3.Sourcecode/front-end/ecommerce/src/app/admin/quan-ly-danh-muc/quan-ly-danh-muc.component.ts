import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { PopupConfirmComponent } from 'src/app/shared/popup-confirm/popup-confirm.component';
import { FilterDate } from '../common/filter-date.model';
import { PageRequest } from '../common/page-request.model';
import { CreateOrEditCategoryComponent } from './modal/create-or-edit-category/create-or-edit-category.component';
import { CategoryRequestModel } from './model/category-request.model';
import { CategoryModel } from './model/category-response.model';
import { CategoryService } from './service/category.service';

@Component({
  selector: 'app-quan-ly-danh-muc',
  templateUrl: './quan-ly-danh-muc.component.html',
  styleUrls: ['./quan-ly-danh-muc.component.scss']
})
export class QuanLyDanhMucComponent implements OnInit {
 
   ref: DynamicDialogRef = new DynamicDialogRef;
   FilterForm!: FormGroup

    // de phan trang va sap xep
    paginator: PageRequest={
      page: 1,
      pageSize: 5,
      sortBy: '',
      condition: ''
    }
    // loc theo thoi gian
    filterDate: FilterDate ={
      createdDateStart: 0,
      createdDateEnd: 0,
      updatedDateStart: 0,
      updatedDateEnd: 0
    }

    categories : any;
    selectedCategory! : CategoryModel[];
    totalRecords =0;

    constructor(private fb: FormBuilder,
                public dialogService: DialogService,
                private messageService: MessageService,
                private categoryService: CategoryService){

    }
    
    ngOnInit(): void {
      this.buildForm();
    }

    buildForm(){
         this.FilterForm = this.fb.group({
           categoryName: [''],
           description: [''],
           createdDate: [0],
           updatedDate: [0]
         })
    }
    getInput(): CategoryRequestModel{
       this.filterDate.createdDateStart = this.FilterForm.controls['createdDate'].value[0]? this.FilterForm.controls['createdDate'].value[0].getTime():0;
       this.filterDate.createdDateEnd = this.FilterForm.controls['createdDate'].value[1]?this.FilterForm.controls['createdDate'].value[1].getTime():0;
       this.filterDate.updatedDateStart = this.FilterForm.controls['updatedDate'].value[0]? this.FilterForm.controls['updatedDate'].value[0].getTime():0;
       this.filterDate.updatedDateEnd = this.FilterForm.controls['updatedDate'].value[1]?this.FilterForm.controls['updatedDate'].value[1].getTime():0;

        const input : CategoryRequestModel ={
          pageRequest: this.paginator,
          filterDate: this.filterDate,
          categoryName: this.FilterForm.controls['categoryName'].value,
          description: this.FilterForm.controls['description'].value
        }
        return input;
    }

    reloadTable(e: any){
      this.paginator.page = e.first==0? 1: (e.first/e.rows +1);
      this.paginator.pageSize = e.rows;
      this.paginator.sortBy = e.sortField;
      this.paginator.condition = e.sortOrder === 1 ? 'desc' : 'asc';
  
      console.log(this.getInput())
      this.getData();
    }

    getData(){
      const input = this.getInput();
      this.categoryService.findCategory(input).subscribe(res =>{
         this.categories = res.data;
         this.totalRecords = res.totalRecords;
         console.log(this.categories)
      })
    }
    
    reset(){
      this.buildForm();
      this.paginator ={
        page: 1,
        pageSize: 5,
        sortBy: '',
        condition: ''
      }
      this.getData();
    }

    search(){
      this.getData();
    }

    createOrEdit(category?: any){
        this.ref = this.dialogService.open(CreateOrEditCategoryComponent, {
        header: category? 'Chi tiết ' : 'Thêm mới ',
        width: '60%',
        height:'80%',
        contentStyle: { 'padding-bottom': '0','height':'100%' },
        baseZIndex: 10000,
        data: { category: category },
      });
      this.ref.onClose.subscribe(() => {
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: '',
          life: 3000,
        });
      });
    }


    delete(){
      const confirm = this.dialogService.open(PopupConfirmComponent, {
        showHeader: false,
        baseZIndex: 10000,
        data: {
          title: 'Bạn có chắc muốn xóa sản phẩm không ?',
          content: '',
          status: 1
        }
      });
  
      confirm.onClose.subscribe(res => {
            if(this.selectedCategory.length != 0 && res === 'yes'){
              const Ids  = this.selectedCategory.map(ele => ele.id);
              this.categoryService.deleteCategory(Ids).subscribe(res =>{
                console.log(res);
                if(res.status === 200){
                    this.messageService.add({
                      severity: 'success',
                      summary: 'Success',
                      detail: '',
                      life: 3000,
                    });
                    this.selectedCategory =[];
                    this.getData();
                }
                else{
                  this.messageService.add({
                    severity: 'error',
                    summary: 'fail',
                    detail: '',
                    life: 3000,
                  });
                }
              })
            }
      })
    }
}
