import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Project, User } from 'src/app/shared';
import { ModalProjectComponent } from '../modal-project/modal-project.component';
import { ProjectService } from '../services/project.service';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

const LS_CHAVE: string = "userSession";

@Component({
  selector: 'app-project-folder',
  templateUrl: './project-folder.component.html',
  styleUrls: ['./project-folder.component.css']
})
export class ProjectFolderComponent implements OnInit {
  projects!: Project[]
  projectId!: number

  mappingNumber: number = 0
  routineNumber: number = 0
  configNumber: number = 0

  loggedUser: User = JSON.parse(localStorage[LS_CHAVE])

  constructor(private projectService: ProjectService, public router: Router, private activatedRoute: ActivatedRoute, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.fillProjects();
    this.projectId = parseInt(this.activatedRoute.snapshot.paramMap.get('line')!)
  }

  fillProjects(): void {
    this.projectService.getAllProjects().subscribe({
      next: (data) => {
        this.projects = data//; this.ProjectsOriginal = JSON.parse(JSON.stringify(data))},
        if(this.projects[this.projectId].mappings!=null) {
          this.mappingNumber = this.projects[this.projectId].mappings!.length
        }
        if(this.projects[this.projectId].routines!=null) {
          this.routineNumber = this.projects[this.projectId].routines!.length
        }
        if(this.projects[this.projectId].configurations!=null) {
          this.configNumber = this.projects[this.projectId].configurations!.length
        }
      },
      error: (err) => console.log(err)
    });
  }

  enable(project: Project):void {
    project.enabled = true
    this.projectService.updateProject(project).subscribe({
      next: (data) => this.projects[project.line!] = project,
      error: (err) => console.log(err)
    });
  }

  remove(project: Project): void {
    this.projectService.remove(project).subscribe({
      next: (data) => this.fillProjects(),
      error: (err) => console.log(err)
    });
  }

  openModal(project: Project) {
    const modalRef = this.modalService.open(ModalProjectComponent)
    modalRef.componentInstance.project = project
    modalRef.componentInstance.projects = this.projects
  }

  openAddModal() {
    const modalRef = this.modalService.open(ModalProjectComponent)
    modalRef.componentInstance.projects = this.projects
  }

  openMappings():void {
    this.router.navigate(['system/mapping/' + this.activatedRoute.snapshot.paramMap.get('line')])
  }

  openRoutines():void {
    this.router.navigate(['system/routine/' + this.activatedRoute.snapshot.paramMap.get('line')])
  }

  openConfigurations():void {

  }

  download(): void {
    this.projectService.download(String(this.projectId)).subscribe({
      next: (data) => {
        console.log(data)
      },
      error: (err) => console.log(err)
    })
  }
}
