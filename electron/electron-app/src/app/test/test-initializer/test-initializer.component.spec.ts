import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestInitializerComponent } from './test-initializer.component';

describe('TestInitializerComponent', () => {
  let component: TestInitializerComponent;
  let fixture: ComponentFixture<TestInitializerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TestInitializerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TestInitializerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
