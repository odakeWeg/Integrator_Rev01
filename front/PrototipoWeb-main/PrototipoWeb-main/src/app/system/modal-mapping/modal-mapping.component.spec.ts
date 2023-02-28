import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalMappingComponent } from './modal-mapping.component';

describe('ModalMappingComponent', () => {
  let component: ModalMappingComponent;
  let fixture: ComponentFixture<ModalMappingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModalMappingComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalMappingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
