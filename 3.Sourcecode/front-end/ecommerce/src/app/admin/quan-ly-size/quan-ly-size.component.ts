import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { PopupConfirmComponent } from 'src/app/shared/popup-confirm/popup-confirm.component';
import { FilterDate } from '../common/filter-date.model';
import { PageRequest } from '../common/page-request.model';
import { CreateOrEditSizeComponent } from './modal/create-or-edit-size/create-or-edit-size.component';
import { SizeAdminRequest } from './model/size-admin-resquest.model';
import { SizeModel } from './model/size.model';
import { SizeService } from './service/size.service';

@Component({
  selector: 'app-quan-ly-size',
  templateUrl: './quan-ly-size.component.html',
  styleUrls: ['./quan-ly-size.component.scss']
})
export class QuanLySizeComponent implements OnInit {

       ref: DynamicDialogRef = new DynamicDialogRef;
       FilterForm!: FormGroup

       sizes : any
       selectedSize!: SizeModel[]
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
        private sizeService: SizeService){

      }

  ngOnInit(): void {
    this.buildForm();
  }

  buildForm(){
    this.FilterForm = this.fb.group({
       sizeName: [''],
       sizeCode: [''],
       createdDate: [0],
       updatedDate: [0]
    })
  }

  getInput(): SizeAdminRequest{

    this.filterDate.createdDateStart = this.FilterForm.controls['createdDate'].value[0]? this.FilterForm.controls['createdDate'].value[0].getTime():0;
    this.filterDate.createdDateEnd = this.FilterForm.controls['createdDate'].value[1]?this.FilterForm.controls['createdDate'].value[1].getTime():0;
    this.filterDate.updatedDateStart = this.FilterForm.controls['updatedDate'].value[0]? this.FilterForm.controls['updatedDate'].value[0].getTime():0;
    this.filterDate.updatedDateEnd = this.FilterForm.controls['updatedDate'].value[1]?this.FilterForm.controls['updatedDate'].value[1].getTime():0;

    const input : SizeAdminRequest ={
      filterDate: this.filterDate,
      pageRequest: this.paginator,
      sizeName: this.FilterForm.controls['sizeName'].value,
      sizeCode: this.FilterForm.controls['sizeCode'].value
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


  createOrEdit(size?: any){
    this.ref = this.dialogService.open(CreateOrEditSizeComponent, {
      header: size? 'Chi tiết ' : 'Thêm mới ',
      width: '60%',
      height:'60%',
      contentStyle: { 'padding-bottom': '0','height':'100%' },
      baseZIndex: 10000,
      data: { size: size },
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
  
  getData(){
    const input = this.getInput();
      this.sizeService.findSize(input).subscribe(res =>{
         this.sizes = res.data;
         this.totalRecords = res.totalRecords;
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
  delete(){
    const confirm = this.dialogService.open(PopupConfirmComponent, {
      showHeader: false,
      baseZIndex: 10000,
      data: {
        title: 'Bạn có chắc muốn xóa Size không ?',
        content: '',
        status: 1
      }
    });

    confirm.onClose.subscribe(res => {
          if(this.selectedSize.length != 0 && res === 'yes'){
            const Ids  = this.selectedSize.map(ele => ele.id);
            this.sizeService.deleteSize(Ids).subscribe(res =>{
              console.log(res);
              if(res.status === 200){
                  this.messageService.add({
                    severity: 'success',
                    summary: 'Success',
                    detail: '',
                    life: 3000,
                  });
                  this.selectedSize =[];
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
