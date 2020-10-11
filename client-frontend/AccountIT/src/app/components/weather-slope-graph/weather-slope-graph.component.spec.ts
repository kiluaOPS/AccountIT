import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WeatherSlopeGraphComponent } from './weather-slope-graph.component';

describe('WeatherSlopeGraphComponent', () => {
  let component: WeatherSlopeGraphComponent;
  let fixture: ComponentFixture<WeatherSlopeGraphComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WeatherSlopeGraphComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WeatherSlopeGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
