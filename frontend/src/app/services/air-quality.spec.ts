import { TestBed } from '@angular/core/testing';

import { AirQuality } from './air-quality.service';

describe('AirQuality', () => {
  let service: AirQuality;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AirQuality);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
