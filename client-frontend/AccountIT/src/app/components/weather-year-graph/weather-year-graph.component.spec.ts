import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WeatherYearGraphComponent } from './weather-year-graph.component';

describe('WeatherYearGraphComponent', () => {
  let component: WeatherYearGraphComponent;
  let fixture: ComponentFixture<WeatherYearGraphComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WeatherYearGraphComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WeatherYearGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
