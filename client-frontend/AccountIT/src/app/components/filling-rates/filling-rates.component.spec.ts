import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FillingRatesComponent } from './filling-rates.component';

describe('FillingRatesComponent', () => {
  let component: FillingRatesComponent;
  let fixture: ComponentFixture<FillingRatesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FillingRatesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FillingRatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
