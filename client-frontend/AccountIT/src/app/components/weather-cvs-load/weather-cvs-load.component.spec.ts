import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WeatherCvsLoadComponent } from './weather-cvs-load.component';

describe('WeatherCvsLoadComponent', () => {
  let component: WeatherCvsLoadComponent;
  let fixture: ComponentFixture<WeatherCvsLoadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WeatherCvsLoadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WeatherCvsLoadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
