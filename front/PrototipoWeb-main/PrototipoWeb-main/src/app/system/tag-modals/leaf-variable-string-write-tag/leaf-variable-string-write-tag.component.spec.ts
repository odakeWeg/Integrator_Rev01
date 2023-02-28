import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeafVariableStringWriteTagComponent } from './leaf-variable-string-write-tag.component';

describe('LeafVariableStringWriteTagComponent', () => {
  let component: LeafVariableStringWriteTagComponent;
  let fixture: ComponentFixture<LeafVariableStringWriteTagComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeafVariableStringWriteTagComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeafVariableStringWriteTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
