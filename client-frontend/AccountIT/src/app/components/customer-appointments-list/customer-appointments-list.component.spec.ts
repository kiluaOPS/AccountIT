import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerAppointmentsListComponent } from './customer-appointments-list.component';

describe('CustomerAppointmentsListComponent', () => {
  let component: CustomerAppointmentsListComponent;
  let fixture: ComponentFixture<CustomerAppointmentsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomerAppointmentsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomerAppointmentsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
