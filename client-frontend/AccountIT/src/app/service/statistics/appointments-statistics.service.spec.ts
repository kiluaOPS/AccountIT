import { TestBed } from '@angular/core/testing';

import { AppointmentsStatisticsService } from './appointments-statistics.service';

describe('AppointmentsStatisticsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AppointmentsStatisticsService = TestBed.get(AppointmentsStatisticsService);
    expect(service).toBeTruthy();
  });
});
