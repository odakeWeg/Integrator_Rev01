import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeafIOLinkCommunicationTagComponent } from './leaf-iolink-communication-tag.component';

describe('LeafIOLinkCommunicationTagComponent', () => {
  let component: LeafIOLinkCommunicationTagComponent;
  let fixture: ComponentFixture<LeafIOLinkCommunicationTagComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeafIOLinkCommunicationTagComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeafIOLinkCommunicationTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
