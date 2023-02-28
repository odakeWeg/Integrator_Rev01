import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeafMultipleRegisterCompareTagComponent } from './leaf-multiple-register-compare-tag.component';

describe('LeafMultipleRegisterCompareTagComponent', () => {
  let component: LeafMultipleRegisterCompareTagComponent;
  let fixture: ComponentFixture<LeafMultipleRegisterCompareTagComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeafMultipleRegisterCompareTagComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeafMultipleRegisterCompareTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
