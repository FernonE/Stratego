import { TestBed } from '@angular/core/testing';

import { SpelerService } from './speler.service';

describe('SpelerService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SpelerService = TestBed.get(SpelerService);
    expect(service).toBeTruthy();
  });
});
