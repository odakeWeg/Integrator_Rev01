import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalTagNotFoundComponent } from './modal-tag-not-found.component';

describe('ModalTagNotFoundComponent', () => {
  let component: ModalTagNotFoundComponent;
  let fixture: ComponentFixture<ModalTagNotFoundComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModalTagNotFoundComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalTagNotFoundComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
