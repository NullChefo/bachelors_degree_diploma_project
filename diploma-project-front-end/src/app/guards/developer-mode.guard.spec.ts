import {TestBed} from '@angular/core/testing';

import {DeveloperModeGuard} from './developer-mode.guard';

describe('DeveloperModeGuard', () => {
  let guard: DeveloperModeGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(DeveloperModeGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
