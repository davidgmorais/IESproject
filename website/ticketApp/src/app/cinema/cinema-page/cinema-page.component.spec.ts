import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CinemaPageComponent } from './cinema-page.component';

describe('CinemaPageComponent', () => {
  let component: CinemaPageComponent;
  let fixture: ComponentFixture<CinemaPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CinemaPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CinemaPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
