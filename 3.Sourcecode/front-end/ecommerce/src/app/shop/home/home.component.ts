import { Component } from '@angular/core';
import {HomeServiceService} from "./home-service/home-service.service";
import {PrimeNGConfig} from "primeng/api";
import {PageRequest} from "../../admin/common/page-request.model";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  slideConfig = {"slidesToShow": 1, "slidesToScroll": 1} ;
  products: any;

  checkProductSize: boolean = false;
  paginator: PageRequest={
    page: 1,
    pageSize: 8,
    sortBy: '',
    condition: ''
  }
  totalRecords: any;

  constructor(private homeService: HomeServiceService,
              private primengConfig: PrimeNGConfig) {
  }

  ngOnInit(): void {
    this.getData();
    this.primengConfig.ripple = true;

  }

  getData(){
    this.homeService.findProduct(this.paginator).subscribe(res =>{
      this.totalRecords = res.totalRecords;
      this.products = res.data;

      if (this.products.length != 0){
        this.checkProductSize = true;
      }
      console.log(res)
      console.log(this.products)
    })
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
