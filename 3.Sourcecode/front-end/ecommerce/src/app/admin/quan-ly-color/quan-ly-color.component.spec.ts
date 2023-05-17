import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuanLyColorComponent } from './quan-ly-color.component';

describe('QuanLyColorComponent', () => {
  let component: QuanLyColorComponent;
  let fixture: ComponentFixture<QuanLyColorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ QuanLyColorComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(QuanLyColorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
