import { Component } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { DynamicDialogRef, DialogService } from 'primeng/dynamicdialog';
import { PopupConfirmComponent } from 'src/app/shared/popup-confirm/popup-confirm.component';
import { FilterDate } from '../common/filter-date.model';
import { PageRequest } from '../common/page-request.model';
import { CreateOrEditColorComponent } from '../quan-ly-color/modal/create-or-edit-color/create-or-edit-color.component';
import { ColorModel } from '../quan-ly-color/model/color.model';
import { CreateOrEditDiscountComponent } from './modal/create-or-edit-discount/create-or-edit-discount.component';
import { DiscountRequest } from './model/discount-request.model';
import { DiscountService } from './service/discount.service';

@Component({
  selector: 'app-quan-ly-discount',
  templateUrl: './quan-ly-discount.component.html',
  styleUrls: ['./quan-ly-discount.component.scss']
})
export class QuanLyDiscountComponent {
  ref: DynamicDialogRef = new DynamicDialogRef;
  FilterForm!: FormGroup
  
  selectedDiscount!: ColorModel[];
  sizes: any;
  totalRecords =0;

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

constructor(private fb: FormBuilder,
 public dialogService: DialogService,
 private messageService: MessageService,
 private discountService: DiscountService){

}

  ngOnInit(): void {
   this.builForm();
 }

 builForm(){
   this.FilterForm = this.fb.group({
     discountName: [''],
     discountPercent: [null],
     createdDate: [0],
     updatedDate: [0]
   })
 }

 getInput(): DiscountRequest{

     this.filterDate.createdDateStart = this.FilterForm.controls['createdDate'].value[0]? this.FilterForm.controls['createdDate'].value[0].getTime():0;
     this.filterDate.createdDateEnd = this.FilterForm.controls['createdDate'].value[1]?this.FilterForm.controls['createdDate'].value[1].getTime():0;
     this.filterDate.updatedDateStart = this.FilterForm.controls['updatedDate'].value[0]? this.FilterForm.controls['updatedDate'].value[0].getTime():0;
     this.filterDate.updatedDateEnd = this.FilterForm.controls['updatedDate'].value[1]?this.FilterForm.controls['updatedDate'].value[1].getTime():0;

     const input : DiscountRequest ={
       filterDate: this.filterDate,
       pageRequest: this.paginator,
       discountName: this.FilterForm.controls['discountName'].value,
       discountPercent: this.FilterForm.controls['discountPercent'].value? this.FilterForm.controls['discountPercent'].value:-1
     }
     
     return input;
 }


 reloadTable(e: any){
   this.paginator.page = e.first==0? 1: (e.first/e.rows +1);
   this.paginator.pageSize = e.rows;
   this.paginator.sortBy = e.sortField;
   this.paginator.condition = e.sortOrder === 1 ? 'desc' : 'asc';
   
   console.log(this.paginator)

   this.getData();
 }

 getData(){
   const input = this.getInput();
   this.discountService.findDiscount(input).subscribe(res =>{
     this.sizes = res.data;
     this.totalRecords = res.totalRecords;

     console.log(this.sizes)
   })
 }

 reset(){
   this.builForm();
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

 createOrEdit(discount?: any){
   this.ref = this.dialogService.open(CreateOrEditDiscountComponent, {
     header: discount? 'Chi tiết ' : 'Thêm mới ',
     width: '60%',
     height:'60%',
     contentStyle: { 'padding-bottom': '0','height':'100%' },
     baseZIndex: 10000,
     data: { discount: discount },
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
       title: 'Bạn có chắc muốn xóa Discount không ?',
       content: '',
       status: 1
     }
   });

   confirm.onClose.subscribe(res => {
         if(this.selectedDiscount.length != 0 && res === 'yes'){
           const Ids  = this.selectedDiscount.map(ele => ele.id);
           this.discountService.deleteDisocunt(Ids).subscribe(res =>{
             console.log(res);
             if(res.status === 200){
                 this.messageService.add({
                   severity: 'success',
                   summary: 'Success',
                   detail: '',
                   life: 3000,
                 });
                 this.selectedDiscount =[];
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
