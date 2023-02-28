import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeafEnsSetupTagComponent } from './leaf-ens-setup-tag.component';

describe('LeafEnsSetupTagComponent', () => {
  let component: LeafEnsSetupTagComponent;
  let fixture: ComponentFixture<LeafEnsSetupTagComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeafEnsSetupTagComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeafEnsSetupTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
