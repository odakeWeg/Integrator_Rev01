import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectFolderComponent } from './project-folder.component';

describe('ProjectFolderComponent', () => {
  let component: ProjectFolderComponent;
  let fixture: ComponentFixture<ProjectFolderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProjectFolderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProjectFolderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
