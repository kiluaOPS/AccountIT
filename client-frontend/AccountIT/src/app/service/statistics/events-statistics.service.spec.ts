import { TestBed } from '@angular/core/testing';

import { EventsStatisticsService } from './events-statistics.service';

describe('EventsStatisticsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EventsStatisticsService = TestBed.get(EventsStatisticsService);
    expect(service).toBeTruthy();
  });
});
