import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvCustomerAppointmentsComponent } from './adv-customer-appointments.component';

describe('AdvCustomerAppointmentsComponent', () => {
  let component: AdvCustomerAppointmentsComponent;
  let fixture: ComponentFixture<AdvCustomerAppointmentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdvCustomerAppointmentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdvCustomerAppointmentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
