import { TestBed } from '@angular/core/testing';

import { SpelService } from './spel.service';

describe('SpelService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SpelService = TestBed.get(SpelService);
    expect(service).toBeTruthy();
  });
});
