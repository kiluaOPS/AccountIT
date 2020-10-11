import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadMainComponent } from './load-main.component';

describe('LoadMainComponent', () => {
  let component: LoadMainComponent;
  let fixture: ComponentFixture<LoadMainComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoadMainComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
