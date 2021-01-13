import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddRoomLayoutComponent } from './add-room-layout.component';

describe('AddRoomLayoutComponent', () => {
  let component: AddRoomLayoutComponent;
  let fixture: ComponentFixture<AddRoomLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddRoomLayoutComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddRoomLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
