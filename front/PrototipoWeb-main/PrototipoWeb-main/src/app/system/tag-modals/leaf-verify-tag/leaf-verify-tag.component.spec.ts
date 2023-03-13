import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeafVerifyTagComponent } from './leaf-verify-tag.component';

describe('LeafVerifyTagComponent', () => {
  let component: LeafVerifyTagComponent;
  let fixture: ComponentFixture<LeafVerifyTagComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeafVerifyTagComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeafVerifyTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
