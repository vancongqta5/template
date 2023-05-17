import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuanLySizeComponent } from './quan-ly-size.component';

describe('QuanLySizeComponent', () => {
  let component: QuanLySizeComponent;
  let fixture: ComponentFixture<QuanLySizeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ QuanLySizeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(QuanLySizeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
