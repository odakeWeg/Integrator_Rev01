import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeafInlineSetupTagComponent } from './leaf-inline-setup-tag.component';

describe('LeafInlineSetupTagComponent', () => {
  let component: LeafInlineSetupTagComponent;
  let fixture: ComponentFixture<LeafInlineSetupTagComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeafInlineSetupTagComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeafInlineSetupTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
