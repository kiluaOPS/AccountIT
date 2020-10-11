import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClinicsUploadComponent } from './clinics-upload.component';

describe('ClinicsUploadComponent', () => {
  let component: ClinicsUploadComponent;
  let fixture: ComponentFixture<ClinicsUploadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClinicsUploadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClinicsUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
