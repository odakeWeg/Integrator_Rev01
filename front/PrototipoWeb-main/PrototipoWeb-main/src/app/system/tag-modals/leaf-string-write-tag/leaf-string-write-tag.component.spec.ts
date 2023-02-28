import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeafStringWriteTagComponent } from './leaf-string-write-tag.component';

describe('LeafStringWriteTagComponent', () => {
  let component: LeafStringWriteTagComponent;
  let fixture: ComponentFixture<LeafStringWriteTagComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeafStringWriteTagComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeafStringWriteTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
