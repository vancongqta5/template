import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import {CartComponent} from './cart/cart.component';
import {CheckoutComponent} from './checkout/checkout.component';
import {ProductSingleComponent} from './product-single/product-single.component';
import {ProfileDetailsComponent} from './profile-details/profile-details.component';
import {ErrorComponent} from './error/error.component';
import { ShopComponent } from './shop.component';
import {ContactComponent} from "./contact/contact.component";

import {ShopPageComponent} from "./shop-page/shop-page.component";

import {FaqComponent} from "./faq/faq.component";
import {OrdersComponent} from "./orders/orders.component";



const routes: Routes = [{path: '', component: HomeComponent},
  {path:'shop-page', component:ShopPageComponent},
  {path: 'cart/:productDetailId', component: CartComponent},
  {path: 'check-out', component: CheckoutComponent},
  {path: 'orders', component: OrdersComponent},
  {path: 'product-single/:productDetailId', component: ProductSingleComponent},
  {path: 'profile-detail', component: ProfileDetailsComponent},
  {path: 'faq',component:FaqComponent},
  {path:'contact',component: ContactComponent},
  {path: '**', component: ErrorComponent}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ShopRoutingModule { }
