import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DynamicScriptLoaderComponent } from './dynamic-script-loader.component';

describe('DynamicScriptLoaderComponent', () => {
  let component: DynamicScriptLoaderComponent;
  let fixture: ComponentFixture<DynamicScriptLoaderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DynamicScriptLoaderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DynamicScriptLoaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
