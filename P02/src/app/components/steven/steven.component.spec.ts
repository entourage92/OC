import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StevenComponent } from './steven.component';

describe('StevenComponent', () => {
  let component: StevenComponent;
  let fixture: ComponentFixture<StevenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StevenComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(StevenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
