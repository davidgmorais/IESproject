import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PremierDetailsComponent } from './premier-details.component';

describe('PremierDetailsComponent', () => {
  let component: PremierDetailsComponent;
  let fixture: ComponentFixture<PremierDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PremierDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PremierDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
