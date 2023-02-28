import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeafVariableWriteTagComponent } from './leaf-variable-write-tag.component';

describe('LeafVariableWriteTagComponent', () => {
  let component: LeafVariableWriteTagComponent;
  let fixture: ComponentFixture<LeafVariableWriteTagComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeafVariableWriteTagComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeafVariableWriteTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
