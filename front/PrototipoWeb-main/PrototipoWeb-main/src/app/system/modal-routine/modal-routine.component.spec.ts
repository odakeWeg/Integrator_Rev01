import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalRoutineComponent } from './modal-routine.component';

describe('ModalRoutineComponent', () => {
  let component: ModalRoutineComponent;
  let fixture: ComponentFixture<ModalRoutineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModalRoutineComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalRoutineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
