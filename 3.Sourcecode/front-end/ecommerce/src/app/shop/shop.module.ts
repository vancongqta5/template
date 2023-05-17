import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ShopRoutingModule } from './shop-routing.module';
import { ShopComponent } from './shop.component';
import { HomeComponent } from './home/home.component';

import { SlickCarouselModule } from 'ngx-slick-carousel';
import { CartComponent } from './cart/cart.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { ErrorComponent } from './error/error.component';
import { OrdersComponent } from './orders/orders.component';
import { ProductSingleComponent } from './product-single/product-single.component';
import { ProfileDetailsComponent } from './profile-details/profile-details.component';
import {SharedModule} from "../shared/shared.module";
import { ContactComponent } from './contact/contact.component';

import { ShopPageComponent } from './shop-page/shop-page.component';
// import { DataviewComponent } from './shop-page/dataview/dataview.component';

import { FaqComponent } from './faq/faq.component';

@NgModule({
  declarations: [
    ShopComponent,
    HomeComponent,
    CartComponent,
    CheckoutComponent,
    ErrorComponent,
    OrdersComponent,
    ProductSingleComponent,
    ProfileDetailsComponent,
    ContactComponent,
    ShopPageComponent,
    FaqComponent

  ],
    imports: [
        CommonModule,
        ShopRoutingModule,
        SlickCarouselModule,
        SharedModule
    ]
})
export class ShopModule { }
