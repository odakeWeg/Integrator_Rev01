import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeafTestTagComponent } from './leaf-test-tag.component';

describe('LeafTestTagComponent', () => {
  let component: LeafTestTagComponent;
  let fixture: ComponentFixture<LeafTestTagComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeafTestTagComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeafTestTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
