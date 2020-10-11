import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EventsUploadComponent } from './events-upload.component';

describe('EventsUploadComponent', () => {
  let component: EventsUploadComponent;
  let fixture: ComponentFixture<EventsUploadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EventsUploadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventsUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
