import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuanLyDiscountComponent } from './quan-ly-discount.component';

describe('QuanLyDiscountComponent', () => {
  let component: QuanLyDiscountComponent;
  let fixture: ComponentFixture<QuanLyDiscountComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ QuanLyDiscountComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(QuanLyDiscountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
