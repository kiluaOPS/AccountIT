import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InjuriesRecoveryTimeComponent } from './injuries-recovery-time.component';

describe('InjuriesRecoveryTimeComponent', () => {
  let component: InjuriesRecoveryTimeComponent;
  let fixture: ComponentFixture<InjuriesRecoveryTimeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InjuriesRecoveryTimeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InjuriesRecoveryTimeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
