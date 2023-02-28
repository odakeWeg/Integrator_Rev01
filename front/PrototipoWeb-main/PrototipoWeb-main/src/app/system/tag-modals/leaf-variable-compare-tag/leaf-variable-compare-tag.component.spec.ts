import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeafVariableCompareTagComponent } from './leaf-variable-compare-tag.component';

describe('LeafVariableCompareTagComponent', () => {
  let component: LeafVariableCompareTagComponent;
  let fixture: ComponentFixture<LeafVariableCompareTagComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeafVariableCompareTagComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeafVariableCompareTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
