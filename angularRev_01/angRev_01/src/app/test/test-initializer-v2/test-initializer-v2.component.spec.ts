import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestInitializerV2Component } from './test-initializer-v2.component';

describe('TestInitializerV2Component', () => {
  let component: TestInitializerV2Component;
  let fixture: ComponentFixture<TestInitializerV2Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TestInitializerV2Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TestInitializerV2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
