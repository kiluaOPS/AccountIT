import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomersAgeDistributionComponent } from './customers-age-distribution.component';

describe('CustomersAgeDistributionComponent', () => {
  let component: CustomersAgeDistributionComponent;
  let fixture: ComponentFixture<CustomersAgeDistributionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomersAgeDistributionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomersAgeDistributionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
