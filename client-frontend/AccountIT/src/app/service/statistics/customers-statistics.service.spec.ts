import { TestBed } from '@angular/core/testing';

import { CustomersStatisticsService } from './customers-statistics.service';

describe('CustomersStatisticsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CustomersStatisticsService = TestBed.get(CustomersStatisticsService);
    expect(service).toBeTruthy();
  });
});
