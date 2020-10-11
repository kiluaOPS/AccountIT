import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppointmentsUploadComponent } from './appointments-upload.component';

describe('AppointmentsUploadComponent', () => {
  let component: AppointmentsUploadComponent;
  let fixture: ComponentFixture<AppointmentsUploadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppointmentsUploadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppointmentsUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
