import { TestBed } from '@angular/core/testing';

import { InjuriesStatisticsService } from './injuries-statistics.service';

describe('InjuriesStatisticsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: InjuriesStatisticsService = TestBed.get(InjuriesStatisticsService);
    expect(service).toBeTruthy();
  });
});
