import { TestBed } from '@angular/core/testing';
import { SessionService } from './session.service';
import { SessionInformation } from '../interfaces/sessionInformation.interface';

describe('SessionService', () => {
  let service: SessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SessionService]
    });
    service = TestBed.inject(SessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should log in a user', () => {
    const user: SessionInformation = {
      token: 'mock-token',
      type: 'mock-type',
      id: 1,
      username: 'mock-username',
      firstName: 'Mock',
      lastName: 'User',
      admin: true
    };

    service.logIn(user);

    expect(service.isLogged).toBe(true);
    expect(service.sessionInformation).toEqual(user);
  });

  it('should log out a user', () => {
    service.isLogged = true;
    service.sessionInformation = {
      token: 'mock-token',
      type: 'mock-type',
      id: 1,
      username: 'mock-username',
      firstName: 'Mock',
      lastName: 'User',
      admin: true
    };

    service.logOut();

    expect(service.isLogged).toBe(false);
    expect(service.sessionInformation).toBeUndefined();
  });
});
