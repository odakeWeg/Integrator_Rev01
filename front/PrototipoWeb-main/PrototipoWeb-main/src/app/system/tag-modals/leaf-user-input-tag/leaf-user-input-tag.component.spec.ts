import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeafUserInputTagComponent } from './leaf-user-input-tag.component';

describe('LeafUserInputTagComponent', () => {
  let component: LeafUserInputTagComponent;
  let fixture: ComponentFixture<LeafUserInputTagComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeafUserInputTagComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeafUserInputTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
