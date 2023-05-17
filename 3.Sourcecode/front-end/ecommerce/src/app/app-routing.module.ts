import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminGuard } from './security/admin.guard';
import { AuthGuard } from './security/auth.guard';
import { ErrorComponent } from './shop/error/error.component';
import { HomeComponent } from './shop/home/home.component';


const routes: Routes = [{ path: 'auth', loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule) },
  { path: 'admin', loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule),  canActivate: [AdminGuard] },
  {path: 'shop', loadChildren: () => import('./shop/shop.module').then(m => m.ShopModule), canActivate: [AuthGuard]},
  {path: '', component: HomeComponent}, { path: '**', component: ErrorComponent }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
