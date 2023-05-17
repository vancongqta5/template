import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { PopupConfirmComponent } from 'src/app/shared/popup-confirm/popup-confirm.component';
import { PageRequest } from '../common/page-request.model';
import { CustomerRequest } from './model/customer-request.model';
import { CustomerResponse } from './model/customer-response.model';
import { CustomerService } from './service/customer.service';


@Component({
  selector: 'app-quan-ly-khach-hang',
  templateUrl: './quan-ly-khach-hang.component.html',
  styleUrls: ['./quan-ly-khach-hang.component.scss']
})
export class QuanLyKhachHangComponent implements OnInit {
  ref: DynamicDialogRef = new DynamicDialogRef;
  filterForm!: FormGroup
  
  customers: any;
  totalRecords=0;
  isLoading = false;
  selectedCustomer! : CustomerResponse[];

  status = [
    {code: -1, name: 'Tất cả'},
    {code: 1, name: 'Enable'},
    {code: 0, name: 'Disable'},

  ]

  paginator: PageRequest={
    page: 1,
    pageSize: 5,
    sortBy: '',
    condition: 'desc'
  }

  constructor(
    public dialogService: DialogService,
    private messageService: MessageService,
    private customerService: CustomerService,
    private fb: FormBuilder,
  ){

  }

  ngOnInit(): void {
      this.buildForm();
      this.selectedCustomer =[];
      
  }
  isCollapseFilter = false;
  isEnterpriseTab = true;

  onClickCollapseFilter(event:any) {
    this.isCollapseFilter = event.collapsed;
  }


  reloadTable(e: any){
    this.paginator.page = e.first==0? 1: (e.first/e.rows +1);
    this.paginator.pageSize = e.rows;
    this.paginator.sortBy = e.sortField;
    this.paginator.condition = e.sortOrder === 1 ? 'desc' : 'asc';
    
    console.log(this.paginator)

    this.getData();
  }

  getInput(): CustomerRequest{
      const input: CustomerRequest ={
        pageRequest: this.paginator,
        textSearch: this.filterForm.controls['textSearch'].value,
        phoneNumber: this.filterForm.controls['phoneNumber'].value,
        status:  this.filterForm.controls['status'].value
      }
      return input;
  }

  getData() {
      const input = this.getInput();
      this.customerService.findCustomer(input).subscribe(res =>{
        this.customers = res.data;
        this.totalRecords = res.totalRecords;

        console.log(this.customers)
      })
  }

  buildForm(){
    this.filterForm = this.fb.group({
      textSearch: [''],
      phoneNumber: [''],
      status: [-1]
    })
  }

  refresh(){
     this.buildForm();
     this.paginator={
      page: 1,
      pageSize: 5,
      sortBy: '',
      condition: 'desc'
    }
    this.getData();
  }

 filterData(e: any){
   this.getData();

 }

 changeStatus(status?: any){
  const confirm = this.dialogService.open(PopupConfirmComponent, {
    showHeader: false,
    baseZIndex: 10000,
    data: {
      title: status?'Bạn có chắc muốn kích hoạt cho người dùng này?':'Bạn có chắc muốn khóa người dùng này?',
      content: '',
      status: 1
    }
  });

  confirm.onClose.subscribe(res => {
        if(this.selectedCustomer.length != 0 && res === 'yes'){
          const Ids  = this.selectedCustomer.map(ele => ele.id);
          this.customerService.changeStatus(status, Ids).subscribe(res =>{
            this.selectedCustomer =[];
            if(res.status === 200){this.messageService.add({
                  severity: 'success',
                  summary: 'Success',
                  detail: '',
                  life: 3000,
                });
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

 exportExcel(){
  const input = this.getInput();
  const fileName = 'customer.xlsx';
  this.customerService.exportExcel(fileName, input);
 }
  
}
