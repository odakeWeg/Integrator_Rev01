import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeafUserConfirmationTagComponent } from './leaf-user-confirmation-tag.component';

describe('LeafUserConfirmationTagComponent', () => {
  let component: LeafUserConfirmationTagComponent;
  let fixture: ComponentFixture<LeafUserConfirmationTagComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeafUserConfirmationTagComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeafUserConfirmationTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
