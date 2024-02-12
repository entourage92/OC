import {HttpClientModule} from '@angular/common/http';
import {ComponentFixture, TestBed} from '@angular/core/testing';
import {ReactiveFormsModule} from '@angular/forms';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';
import {RouterTestingModule,} from '@angular/router/testing';
import {expect} from '@jest/globals';
import {SessionService} from '../../../../services/session.service';

import {DetailComponent} from './detail.component';
import {SessionApiService} from "../../services/session-api.service";
import {Router} from "@angular/router";
import {of} from "rxjs";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {TeacherService} from "../../../../services/teacher.service";
import {Teacher} from "../../../../interfaces/teacher.interface";
import {Session} from "../../interfaces/session.interface";
import {SessionInformation} from "../../../../interfaces/sessionInformation.interface";

describe('DetailComponent', () => {
    let component: DetailComponent;
    let fixture: ComponentFixture<DetailComponent>;
    let service: SessionService;
    let router: Router;
    let navigateSpy: jest.SpyInstance;
    let mockmatbar:  any;
    let mockteacherservice: any;
    let mocksessionApiService :  any;
    let mockSessionService :  any;

    const mockSession: Session = {
        id: 22,
        teacher_id: 22,
        users: [20, 21, 22],
        name: "testsession",
        description: "this is a mocked session",
        date: new Date()
    };
    const mockTeacher: Teacher = {
        id: 22,
        lastName: 'Mock Teacher Name',
        firstName: 'Mock Teacher Firstname',
        createdAt: new Date(),
        updatedAt: new Date()
    };

    beforeEach(async () => {
       mockmatbar = {
            open: jest.fn()
        }
        mockteacherservice = {
            detail: jest.fn(() => of({} as Teacher)),
        }
       mocksessionApiService = {
            delete: jest.fn(() => of({})),
            detail: jest.fn(() => of({
                teacher_id : 1,
                users : [2]
            } as Session)),
            participate: jest.fn(() => of()),
            unParticipate: jest.fn(() => of()),
        }
        mockSessionService = {
            sessionInformation: {
                admin: true,
                id: 1
            } as SessionInformation
        }

        await TestBed.configureTestingModule({
            imports: [
                RouterTestingModule.withRoutes(  [{path: 'sessions', redirectTo: ""}]),
                HttpClientTestingModule,
                MatSnackBarModule,
                ReactiveFormsModule
            ],
            declarations: [DetailComponent],
            providers: [
                {provide: SessionService, useValue: mockSessionService},
                {provide: MatSnackBar, useValue: mockmatbar},
                {provide: SessionApiService, useValue: mocksessionApiService},
                {provide: TeacherService, useValue: mockteacherservice},
            ],
        })
            .compileComponents();
        service = TestBed.inject(SessionService);
        fixture = TestBed.createComponent(DetailComponent);
        component = fixture.componentInstance;
        router = TestBed.inject(Router);
        navigateSpy = jest.spyOn(router, 'navigate');
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
    it('should delete session', () => {
        component.delete()
        expect(mockmatbar.open).toHaveBeenCalled()
        expect(navigateSpy).toHaveBeenCalledWith(['sessions']);
    });
    it('should participate to a session', () => {
       component.participate()
       expect(mocksessionApiService.participate).toHaveBeenCalledTimes(1)
    });

    it('should unparticipate to a session', () => {
        component.unParticipate()
        expect(mocksessionApiService.unParticipate).toHaveBeenCalledTimes(1)
    });

    it('should fetch a session and not participate', () => {
        mockteacherservice.detail.mockReturnValue(of(mockTeacher));

        component.sessionId = 'mockSessionId';
        component.fetchSession();
        fixture.detectChanges();

        expect(component.isParticipate).toBe(false);
        expect(mockteacherservice.detail).toHaveBeenCalled();
    });

    it('should fetch a session and participate', () => {
        mockteacherservice.detail.mockReturnValue(of(mockTeacher));
        mocksessionApiService.detail.mockReturnValue(of({
            teacher_id : 1,
            users : [1]
        }));

        component.sessionId = 'mockSessionId';

        component.fetchSession();

        fixture.detectChanges();

        expect(component.isParticipate).toBe(true);
        expect(mockteacherservice.detail).toHaveBeenCalled();
    });
});

