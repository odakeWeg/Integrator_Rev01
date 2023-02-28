import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeafMultipleWriteTagComponent } from './leaf-multiple-write-tag.component';

describe('LeafMultipleWriteTagComponent', () => {
  let component: LeafMultipleWriteTagComponent;
  let fixture: ComponentFixture<LeafMultipleWriteTagComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeafMultipleWriteTagComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeafMultipleWriteTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
