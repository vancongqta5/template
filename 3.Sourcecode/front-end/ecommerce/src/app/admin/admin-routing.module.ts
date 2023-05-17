import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ErrorComponent } from '../shop/error/error.component';

import { AdminComponent } from './admin.component';
import { ProfileAdminComponent } from './profile-admin/profile-admin.component';
import { QuanLyColorComponent } from './quan-ly-color/quan-ly-color.component';
import { QuanLyDanhMucComponent } from './quan-ly-danh-muc/quan-ly-danh-muc.component';
import { QuanLyDiscountComponent } from './quan-ly-discount/quan-ly-discount.component';
import { QuanLyDoanhThuComponent } from './quan-ly-doanh-thu/quan-ly-doanh-thu.component';
import { QuanLyKhachHangComponent } from './quan-ly-khach-hang/quan-ly-khach-hang.component';
import { QuanLySanPhamComponent } from './quan-ly-san-pham/quan-ly-san-pham.component';
import { QuanLySizeComponent } from './quan-ly-size/quan-ly-size.component';

const routes: Routes = [
  { path: '', component: AdminComponent,
    children :[
      {path:'', component: QuanLyKhachHangComponent},
      {path: 'quan-ly-khach-hang', component: QuanLyKhachHangComponent},
      {path:'quan-ly-san-pham', component: QuanLySanPhamComponent},
      {path:'quan-ly-danh-muc', component: QuanLyDanhMucComponent},
      {path:'quan-ly-size', component: QuanLySizeComponent},
      {path:'quan-ly-color', component: QuanLyColorComponent},
      {path:'quan-ly-discount', component: QuanLyDiscountComponent},
      {path:'quan-ly-doanh-thu', component: QuanLyDoanhThuComponent},
      {path:'quan-ly-profile', component: ProfileAdminComponent},
      {path:'quan-ly-san-pham', component: QuanLySanPhamComponent},
      {path:'**', component: ErrorComponent}
    ] }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
