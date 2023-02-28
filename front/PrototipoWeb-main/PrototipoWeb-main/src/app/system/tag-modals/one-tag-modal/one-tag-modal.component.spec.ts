import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OneTagModalComponent } from './one-tag-modal.component';

describe('OneTagModalComponent', () => {
  let component: OneTagModalComponent;
  let fixture: ComponentFixture<OneTagModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OneTagModalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OneTagModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
