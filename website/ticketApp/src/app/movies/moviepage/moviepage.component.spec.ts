import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MoviepageComponent } from './moviepage.component';

describe('MoviepageComponent', () => {
  let component: MoviepageComponent;
  let fixture: ComponentFixture<MoviepageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MoviepageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MoviepageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
