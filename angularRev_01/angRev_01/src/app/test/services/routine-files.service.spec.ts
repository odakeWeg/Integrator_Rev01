import { TestBed } from '@angular/core/testing';

import { RoutineFilesService } from './routine-files.service';

describe('RoutineFilesService', () => {
  let service: RoutineFilesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RoutineFilesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
