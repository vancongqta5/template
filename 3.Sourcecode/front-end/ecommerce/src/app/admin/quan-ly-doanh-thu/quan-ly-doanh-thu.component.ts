import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { FilterDate } from '../common/filter-date.model';
import { PageRequest } from '../common/page-request.model';
import { ProductAdminService } from '../quan-ly-san-pham/service/product-admin.service';
import { AdminRevenueReq } from './model/AdminRevenueReq.model';
import { RevenueService } from './service/revenue.service';

@Component({
  selector: 'app-quan-ly-doanh-thu',
  templateUrl: './quan-ly-doanh-thu.component.html',
  styleUrls: ['./quan-ly-doanh-thu.component.scss']
})
export class QuanLyDoanhThuComponent implements OnInit {
  FormFilter!: FormGroup

  revenue : any
  totalRecords =0;

  category= [
    {
      code: 0,
      name: ''
    }
  ] 


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
                private productAdminService: ProductAdminService,
                private revenuService: RevenueService){

    }

    buildForm(){
      this.FormFilter = this.fb.group({
        productName: [''],
        category: [[]],
        date: [0]
      })
    }

  
  ngOnInit(): void {
     this.buildForm();

     forkJoin([
      this.productAdminService.getAllCategories(),
   ]).subscribe(result =>{
     this.category =[];

     result[0].data.forEach((res: { id: any; categoryName: any; }) =>{
      this.category.push({code: res.id, name: res.categoryName});
     })
    })

     this.getData()
  }


  getInput(): AdminRevenueReq{
     console.log(this.FormFilter.controls['category'])

     this.filterDate.createdDateStart = this.FormFilter.controls['date'].value[0]? this.FormFilter.controls['date'].value[0].getTime():0;
     this.filterDate.createdDateEnd = this.FormFilter.controls['date'].value[1]?this.FormFilter.controls['date'].value[1].getTime():0;
      const input: AdminRevenueReq ={
        filterDate: this.filterDate,
        pageRequest: this.paginator,
        productName: this.FormFilter.controls['productName'].value,
        idsCategory: this.FormFilter.controls['category']? this.FormFilter.controls['category'].value:[]
      }
      return input;
  }

  reloadTable(e: any){
    this.paginator.page = e.first==0? 1: (e.first/e.rows +1);
    this.paginator.pageSize = e.rows;
    this.paginator.sortBy = e.sortField;
    this.paginator.condition = e.sortOrder === 1 ? 'desc' : 'asc';
    this.getData();
  }


  getData(){
     const input = this.getInput();
     console.log(input)
    this.revenuService.findRevenue(input).subscribe(res =>{
      console.log(res)
      this.revenue = res.data;
      this.totalRecords = res.totalRecords
    })
  }

  search(){
    this.paginator ={
      page: 1,
      pageSize: 5,
      sortBy: '',
      condition: ''
    }
    this.getData()
  }

  reset(){
    this.ngOnInit();
  }

  exportExcel(){
    const fileName = 'revenue.xlsx';
    this.revenuService.exportExcel(fileName);
   }

}
