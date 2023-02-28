import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeafEthernetCommunicationTagComponent } from './leaf-ethernet-communication-tag.component';

describe('LeafEthernetCommunicationTagComponent', () => {
  let component: LeafEthernetCommunicationTagComponent;
  let fixture: ComponentFixture<LeafEthernetCommunicationTagComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeafEthernetCommunicationTagComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeafEthernetCommunicationTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
