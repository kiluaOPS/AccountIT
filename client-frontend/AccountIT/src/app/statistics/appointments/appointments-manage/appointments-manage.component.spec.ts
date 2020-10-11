import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppointmentsManageComponent } from './appointments-manage.component';

describe('AppointmentsManageComponent', () => {
  let component: AppointmentsManageComponent;
  let fixture: ComponentFixture<AppointmentsManageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppointmentsManageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppointmentsManageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
