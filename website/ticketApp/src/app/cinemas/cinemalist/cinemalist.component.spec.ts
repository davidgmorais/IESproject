import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CinemalistComponent } from './cinemalist.component';

describe('CinemalistComponent', () => {
  let component: CinemalistComponent;
  let fixture: ComponentFixture<CinemalistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CinemalistComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CinemalistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
