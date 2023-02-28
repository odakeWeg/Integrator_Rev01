import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeafRegisterCompareTagComponent } from './leaf-register-compare-tag.component';

describe('LeafRegisterCompareTagComponent', () => {
  let component: LeafRegisterCompareTagComponent;
  let fixture: ComponentFixture<LeafRegisterCompareTagComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeafRegisterCompareTagComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeafRegisterCompareTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
