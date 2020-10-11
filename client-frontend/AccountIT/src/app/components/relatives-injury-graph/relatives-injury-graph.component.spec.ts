import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RelativesInjuryGraphComponent } from './relatives-injury-graph.component';

describe('RelativesInjuryGraphComponent', () => {
  let component: RelativesInjuryGraphComponent;
  let fixture: ComponentFixture<RelativesInjuryGraphComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RelativesInjuryGraphComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RelativesInjuryGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
