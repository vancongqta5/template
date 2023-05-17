import { Component } from '@angular/core';
import {PrimeNGConfig, SelectItem} from "primeng/api";
import {PageRequest} from "../../admin/common/page-request.model";
import {ShopServiceService} from "./shop-service/shop-service.service";
import {FilterDate} from "../../admin/common/filter-date.model";
import {ProductAdminReQuestModel} from "../../admin/quan-ly-san-pham/model/product-admin-request.model";
import {FormBuilder, FormGroup} from "@angular/forms";
import {ProductRequest} from "./shop-model/shop-request.model";
import {sortByCost} from "./shop-model/sort-product.model";

@Component({
  selector: 'app-shop-page',
  templateUrl: './shop-page.component.html',
  styleUrls: ['./shop-page.component.scss']
})
export class ShopPageComponent {


  FilterForm!: FormGroup

  cities: any;

  selectedCity :any;

  products: any;
  sortOptions!: SelectItem[];

  sortOrder!: number;

  sortField!: string;
  sortKey: any;


  paginator: PageRequest={
    page: 1,
    pageSize: 6,
    sortBy: "",
    condition:  "asc"
  }
  totalRecords!: number;

  value1: any;


  constructor( private primengConfig: PrimeNGConfig,
               private shopService: ShopServiceService,
               private fb: FormBuilder) {
    this.cities = [
      {name: 'Sort by price: low to high', code: 'desc'},
      {name: 'Sort by price: high to low', code: 'asc'},
    ];
  }

  ngOnInit() {
    this.buildForm();
    this.getData();
    this.primengConfig.ripple = true;
  }
  getData(){
    const input = this.getParam();

    // console.log(this.FilterForm.controls['sort'].value)
    console.log(input)
    this.shopService.findProductForShop(input).subscribe(res =>{
      this.products = res.data;
      this.totalRecords = res.totalRecords;
      // console.log(res)
      console.log(this.products);
    })
  }

  buildForm(){
    this.FilterForm = this.fb.group({
      productName: [''],
      cost: [""],
      categoryId: [null],
      sort:['']
    })
  }

  filterData(e: any){
    this.getData();

  }

  getParam() {
    let getCost = null;
    if ("" ===  this.FilterForm.controls['cost'].value){
      getCost = null
    }else
      getCost = Number(this.FilterForm.controls['cost'].value.trim()) ;
    this.paginator.condition = this.FilterForm.controls['sort'].value.code ?? 'asc';
    const input: ProductRequest = {
      pageRequest: this.paginator,
      productName: this.FilterForm.controls['productName'].value,
      cost: getCost ?? 0,
      categoryId: this.FilterForm.controls['categoryId'].value ?? 0,
    }
    return input;

  }



  paginate(event: { first: any; rows: any; page: any; pageCount: number; }) {
    this.paginator.page = event.page+1;
    this.getData();

    console.log(event)
  }
  changePage(page: number){
    this.paginator.page = page;
  }
}
