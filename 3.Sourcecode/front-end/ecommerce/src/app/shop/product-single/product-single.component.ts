import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { forkJoin } from 'rxjs';
import { PageRequest } from 'src/app/admin/common/page-request.model';
import { ProductAdminService } from 'src/app/admin/quan-ly-san-pham/service/product-admin.service';
import { TokenService } from 'src/app/common/token-service/token.service';
import { ProductDetail } from './model/product-detail.model';
import { ProductReviewReq } from './model/product-review-req.model';
import { ProductReviewRes } from './model/product-review-res.model';
import { ProductDetailService } from './service/product-detail.service';

export interface FormAddReview{
  userId: number;
  productId: number;
  comment: string;
}

@Component({
  selector: 'app-product-single',
  templateUrl: './product-single.component.html',
  styleUrls: ['./product-single.component.scss']
})
export class ProductSingleComponent implements OnInit{
  
  AddForm!: FormGroup
  productId : any;
  rating = [1];
  
  product : ProductDetail ={
    id: -1,
    productName: '',
    cost: 0,
    shortDescription: '',
    description: '',
    categoryName: '',
    colorDTOList: [
      {
        id: 0,
        colorName: ''
      }
    ],

    sizeDTOList: [
      {
        id: 0,
        sizeCode: ''
      }
    ],
    imageDTOList: [
      {
        id : 0,
        url: ''
      },
      {
        id : 0,
        url: ''
      },
      {
        id : 0,
        url: ''
      }
    ]
  }
 
  reviewProduct :any
  totalRecord =0;
  paginator: PageRequest ={
    page: 1,
    pageSize: 3,
    sortBy: '',
    condition: ''
  }
  
  request: ProductReviewReq ={
    pageRequest : this.paginator,
    id: 30
  }



  constructor(private route: ActivatedRoute,
    private tokenService: TokenService,
    private productDetailService: ProductDetailService,
    private fb: FormBuilder
   ){
  }
  
  ngOnInit(): void {
    this.productId = this.route.snapshot.paramMap.get('productDetailId')
    if(this.productId){
     this.getProductDetail(Number(this.productId));
    }
    this.reviewProduct =[]; 
    this.getReview();
    this.buildForm();
  }

  buildForm(){
    this.AddForm = this.fb.group({
      comment: ['', Validators.compose([Validators.required])]
    })
  }

  getReview(){
    const input : ProductReviewReq ={
      pageRequest : this.paginator,
      id: this.productId
    }
    this.productDetailService.findReviewProduct(input).subscribe(res =>{
      this.reviewProduct= res.data
      this.totalRecord = res.totalRecords;
      console.log(res.data)
    })
  }

  getProductDetail(id: number){
    if(id != null){
      this.productDetailService.findProductById(id).subscribe(res =>{
        this.product = res.data;
        console.log(this.product)
      })
    }
  }
  changePage(event: any){
    this.paginator.page = event.page+1;
    this.getReview();
  }

  getInput(){
    const input: FormAddReview ={
      userId: Number(this.tokenService.getID()),
      productId: this.productId,
      comment: this.AddForm.controls['comment'].value
    }
    return input;
  }

  addReview(){
   console.log(this.AddForm.controls['comment'].value)

   if(this.tokenService.getID()!= null && this.AddForm.controls['comment'] && this.productId){
      const input = this.getInput();
      this.productDetailService.addReviewProduct(input).subscribe(res =>{
         this.getReview();
         this.buildForm();
      })
   }
  }

}
