import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { forkJoin } from 'rxjs';
import { ImportFileComponent } from 'src/app/shared/import-file/import-file.component';
import { PopupConfirmComponent } from 'src/app/shared/popup-confirm/popup-confirm.component';
import { FilterDate } from '../common/filter-date.model';
import { PageRequest } from '../common/page-request.model';
import { CreateOrEditProductComponent } from './modal/create-or-edit-product/create-or-edit-product.component';
import { ProductAdminReQuestModel } from './model/product-admin-request.model';
import { ProductAdminResponse } from './model/product-admin-response.model';
import { ProductAdminService } from './service/product-admin.service';

@Component({
  selector: 'app-quan-ly-san-pham',
  templateUrl: './quan-ly-san-pham.component.html',
  styleUrls: ['./quan-ly-san-pham.component.scss']
})
export class QuanLySanPhamComponent implements OnInit {
  ref: DynamicDialogRef = new DynamicDialogRef;
  FilterForm!: FormGroup
  
  products: any;
  totalRecords=0;
  isLoading = false;
  selectedProduct! : ProductAdminResponse[]

  category= [
    {
      code: 0,
      name: ''
    }
  ] 

  
  // de phan trang va sap xep
  paginator: PageRequest={
    page: 1,
    pageSize: 10,
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

  createOrEdit(product?: any){
    console.log('vao day')
    this.ref = this.dialogService.open(CreateOrEditProductComponent, {
      header: product? 'Chi tiết sản phẩm' : 'Thêm mới sản phẩm',
      width: '80%',
      height:'100%',
      contentStyle: { 'padding-bottom': '0','height':'100%' },
      baseZIndex: 10000,
      data: { product: product },
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
           console.log(res)
          if(this.selectedProduct.length != 0 && res === 'yes'){
            const Ids  = this.selectedProduct.map(ele => ele.id);
            this.productAdminService.deleteProduct(Ids).subscribe(res =>{
              console.log(res);
              if(res.status === 200){
                  this.messageService.add({
                    severity: 'success',
                    summary: 'Success',
                    detail: '',
                    life: 3000,
                  });
                  this.selectedProduct =[];
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



  gender =[
    {code: 0, name: 'Nam'},
    {code: 1, name: 'Nữ'}
  ]

  constructor(
    public dialogService: DialogService,
    private messageService: MessageService,
    private productAdminService: ProductAdminService,
    private fb: FormBuilder,
  ){

  }

  ngOnInit(): void {
    //  this.getData();
    this.buildForm();

    forkJoin([
      this.productAdminService.getAllCategories(),
   ]).subscribe(result =>{
     this.category =[];
     result[0].data.forEach(res =>{
      this.category.push({code: res.id, name: res.categoryName});
     })
    })


      
  }
  isCollapseFilter = false;
  isEnterpriseTab = true;
  
  buildForm(){
        this.FilterForm = this.fb.group({
          productName: [''],
          quantity:[null],
          cost:[null],
          categoryId:[0],
          createdDate: [0],
          updatedDate:[0]
        })
  }


  getData(){
    const input = this.getParam();
    this.productAdminService.findProduct(input).subscribe(res =>{
      this.totalRecords = res.totalRecords;
      this.products = res.data;

      console.log(this.products)
      // this.isLoading = true;
      if(this.products.length != 0) this.isLoading = false
    })
    // this.isLoading = true;
  }

  getParam(){
    this.filterDate.createdDateStart = this.FilterForm.controls['createdDate'].value[0]? this.FilterForm.controls['createdDate'].value[0].getTime():0;
    this.filterDate.createdDateEnd = this.FilterForm.controls['createdDate'].value[1]?this.FilterForm.controls['createdDate'].value[1].getTime():0;
    this.filterDate.updatedDateStart = this.FilterForm.controls['updatedDate'].value[0]? this.FilterForm.controls['updatedDate'].value[0].getTime():0;
    this.filterDate.updatedDateEnd = this.FilterForm.controls['updatedDate'].value[1]?this.FilterForm.controls['updatedDate'].value[1].getTime():0;
    
    const input : ProductAdminReQuestModel ={
      filterDate: this.filterDate,
      pageRequest: this.paginator,
      productName: this.FilterForm.controls['productName'].value,
      quantity: this.FilterForm.controls['quantity'].value??0,
      cost: this.FilterForm.controls['cost'].value??0,
      categoryId: this.FilterForm.controls['categoryId'].value??0,
    }

    return input;
  }



  onClickCollapseFilter(event:any) {
    this.isCollapseFilter = event.collapsed;
  }

  reloadTable(e: { first: any; rows: any; sortField: any; sortOrder: number; }){

    this.paginator.page = e.first==0? 1: (e.first/e.rows +1);
    this.paginator.pageSize = e.rows;
    this.paginator.sortBy = e.sortField;
    this.paginator.condition = e.sortOrder === 1 ? 'desc' : 'asc';
    this.getData();
    // console.log(this.getParam())
  }

  search(){
    this.getData();
  }
  reset(){
    this.buildForm();
    this.getData();
  }


  exportExcel(){
    const fileName = 'product.xlsx';
    this.productAdminService.exportExcel(fileName);
  }

  importExcel(){
      const URL_IMPORT = this.productAdminService.getApiImport();
      if (URL_IMPORT == null || URL_IMPORT == undefined) return;

      this.ref = this.dialogService.open(ImportFileComponent, {
      header: 'Import',
      width: '45%',
      contentStyle: { 'padding-bottom': '0' },
      baseZIndex: 10000,
      data: {
        url_import: URL_IMPORT
      },
    });
    this.ref.onClose.subscribe(res =>{
      this.getData();
    }
    );
  }

}
