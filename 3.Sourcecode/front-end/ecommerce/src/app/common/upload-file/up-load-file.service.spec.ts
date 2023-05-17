import { TestBed } from '@angular/core/testing';

import { UpLoadFileService } from './up-load-file.service';

describe('UpLoadFileService', () => {
  let service: UpLoadFileService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UpLoadFileService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
