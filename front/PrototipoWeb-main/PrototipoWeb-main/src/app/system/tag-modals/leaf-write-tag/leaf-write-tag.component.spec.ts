import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeafWriteTagComponent } from './leaf-write-tag.component';

describe('LeafWriteTagComponent', () => {
  let component: LeafWriteTagComponent;
  let fixture: ComponentFixture<LeafWriteTagComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeafWriteTagComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeafWriteTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
