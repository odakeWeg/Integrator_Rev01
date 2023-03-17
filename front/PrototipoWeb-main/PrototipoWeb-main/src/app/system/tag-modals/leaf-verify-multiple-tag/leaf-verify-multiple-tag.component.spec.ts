import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeafVerifyMultipleTagComponent } from './leaf-verify-multiple-tag.component';

describe('LeafVerifyMultipleTagComponent', () => {
  let component: LeafVerifyMultipleTagComponent;
  let fixture: ComponentFixture<LeafVerifyMultipleTagComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeafVerifyMultipleTagComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeafVerifyMultipleTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
