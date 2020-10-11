import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InjuryAgeRecoveryComponent } from './injury-age-recovery.component';

describe('InjuryAgeRecoveryComponent', () => {
  let component: InjuryAgeRecoveryComponent;
  let fixture: ComponentFixture<InjuryAgeRecoveryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InjuryAgeRecoveryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InjuryAgeRecoveryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
