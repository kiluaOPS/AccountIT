import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomersUploadComponent } from './customers-upload.component';

describe('CustomersUploadComponent', () => {
  let component: CustomersUploadComponent;
  let fixture: ComponentFixture<CustomersUploadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomersUploadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomersUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
