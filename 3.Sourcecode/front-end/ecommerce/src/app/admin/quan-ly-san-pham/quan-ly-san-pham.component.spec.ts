import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuanLySanPhamComponent } from './quan-ly-san-pham.component';

describe('QuanLySanPhamComponent', () => {
  let component: QuanLySanPhamComponent;
  let fixture: ComponentFixture<QuanLySanPhamComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ QuanLySanPhamComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(QuanLySanPhamComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
