import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeafModbusCommunicationTagComponent } from './leaf-modbus-communication-tag.component';

describe('LeafModbusCommunicationTagComponent', () => {
  let component: LeafModbusCommunicationTagComponent;
  let fixture: ComponentFixture<LeafModbusCommunicationTagComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeafModbusCommunicationTagComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeafModbusCommunicationTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
