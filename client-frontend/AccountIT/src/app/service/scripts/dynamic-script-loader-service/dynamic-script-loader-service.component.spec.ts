import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DynamicScriptLoaderServiceComponent } from './dynamic-script-loader-service.component';

describe('DynamicScriptLoaderServiceComponent', () => {
  let component: DynamicScriptLoaderServiceComponent;
  let fixture: ComponentFixture<DynamicScriptLoaderServiceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DynamicScriptLoaderServiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DynamicScriptLoaderServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
