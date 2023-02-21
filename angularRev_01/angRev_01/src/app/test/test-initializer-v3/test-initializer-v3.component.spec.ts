import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestInitializerV3Component } from './test-initializer-v3.component';

describe('TestInitializerV3Component', () => {
  let component: TestInitializerV3Component;
  let fixture: ComponentFixture<TestInitializerV3Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TestInitializerV3Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TestInitializerV3Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
