import { TestBed } from '@angular/core/testing';

import { SpeelstukService } from './speelstuk.service';

describe('SpeelstukService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SpeelstukService = TestBed.get(SpeelstukService);
    expect(service).toBeTruthy();
  });
});
