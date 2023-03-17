import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeafVariableMultipleCompareTagComponent } from './leaf-variable-multiple-compare-tag.component';

describe('LeafVariableMultipleCompareTagComponent', () => {
  let component: LeafVariableMultipleCompareTagComponent;
  let fixture: ComponentFixture<LeafVariableMultipleCompareTagComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeafVariableMultipleCompareTagComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeafVariableMultipleCompareTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
