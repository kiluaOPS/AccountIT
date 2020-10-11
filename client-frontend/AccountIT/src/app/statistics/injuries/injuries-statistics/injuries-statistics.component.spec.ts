import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InjuriesStatisticsComponent } from './injuries-statistics.component';

describe('InjuriesStatisticsComponent', () => {
  let component: InjuriesStatisticsComponent;
  let fixture: ComponentFixture<InjuriesStatisticsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InjuriesStatisticsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InjuriesStatisticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
