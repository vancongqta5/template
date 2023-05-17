
import { ActivatedRoute, Router } from '@angular/router';
import { DataService } from 'src/app/common/common/data.service';
import { Component,OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {PrimeNGConfig, SelectItem} from "primeng/api";
import {ShopCartService} from "./shop-cart/shop-cart.service";
import { PageRequest } from 'src/app/admin/common/page-request.model';


@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent  implements OnInit {

  product: any;
  productCart: any;
  value1: any;
  totalRecords =0;
  productId : any

  value = 1;

  paginator : PageRequest ={
    page: 1,
    pageSize: 5,
    sortBy: '',
    condition: ''
  }
  change($event: any){
      console.log($event.target.value);
      console.log($event.target.id);

      this.cartService.findMoney($event.target.id,$event.target.value).subscribe(res =>{
        console.log(res.data)

        for(let product of this.productCart){
          if(product.productId == $event.target.id ) product.cost = res.data
        }
      })
  }


  constructor( private primengConfig: PrimeNGConfig,
               private cartService: ShopCartService,
               private fb: FormBuilder,
               private route: ActivatedRoute,
               private dataService: DataService,
               private router: Router) {
  }


  // ngOnInit() {
  //   this.getData();
  //   this.primengConfig.ripple = true;
  //   this.product = this.dataService.getProducts();
  //   this.product =[
  //     {
  //       id: 1,
  //       count: 12
  //     },
  //     {
  //       id: 2,
  //       count: 30
  //     },
  //     {
  //       id: 3,
  //       count: 24

  //   product!: ProductTransferCard[];

  //   constructor(private dataService: DataService,
  //     private router: Router){
  //   }


    ngOnInit(): void {
      this.product = this.dataService.getProducts();
      this.productId = this.route.snapshot.paramMap.get('productDetailId')
      if(this.productId){
        this.cartService.create(this.productId).subscribe(res =>{
          console.log(res)
        })
      }
      this.product =[
        {
          productId: 50,
          count: 3
        },
        {
          productId: 60,
            count: 3
        },
        {
          productId: 52,
          count: 1
        },
        {
          productId: 53,
          count: 3
        },
        {
          productId: 54,
          count: 7
        },
        {
          productId: 55,
          count: 11
        }
    ]

    this.getData();
  }
  getData(){
    const input = this.paginator;
    this.cartService.findProductForCart(input).subscribe(res =>{
      console.log(res);
      this.productCart = res.data;
      // console.log(this.productCart);
      this.totalRecords = res.totalRecords;
    })
  }


  onPageChange($event: any){
      console.log($event)

      this.paginator.page = $event.page+1;
      this.paginator.pageSize =$event.rows;

      this.getData();
      // console.log(this.paginator)
  }

  checkout(){
    this.dataService.addToCard(this.product)
    this.router.navigate(['/shop/check-out'])
  }

  delete($event: any){
    console.log($event.target.id);
    this.cartService.delete($event.target.id).subscribe(res =>{
      this.getData();
    })
  }

}
