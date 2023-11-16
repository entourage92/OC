import { TestBed } from '@angular/core/testing';

import { ReaddataService } from './readdata.service';

describe('ReaddataService', () => {
  let service: ReaddataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReaddataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
