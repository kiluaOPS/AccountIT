import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InjuriesUploadComponent } from './injuries-upload.component';

describe('InjuriesUploadComponent', () => {
  let component: InjuriesUploadComponent;
  let fixture: ComponentFixture<InjuriesUploadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InjuriesUploadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InjuriesUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
