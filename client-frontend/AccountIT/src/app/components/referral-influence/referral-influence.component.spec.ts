import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReferralInfluenceComponent } from './referral-influence.component';

describe('ReferralInfluenceComponent', () => {
  let component: ReferralInfluenceComponent;
  let fixture: ComponentFixture<ReferralInfluenceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReferralInfluenceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReferralInfluenceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
