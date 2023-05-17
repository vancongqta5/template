import { Injectable } from '@angular/core';
import { ProductTransferCard } from '../utils/product-transfer-card.model';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  products! : ProductTransferCard[];

  constructor() {
    this.products = [];
   }

  addToCard(product?: any){
      this.products.push(product)
  }

  getProducts(){
    return this.products;
  }

  clearCard(){
    this.products = [];
    return this.products;
  }
}
