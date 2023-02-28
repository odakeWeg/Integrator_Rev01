import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Project, User } from 'src/app/shared';
import { ModalProjectComponent } from '../modal-project/modal-project.component';
import { ProjectService } from '../services/project.service';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

const LS_CHAVE: string = "userSession";

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {
  projects: Project[] = []

  idMin!: Number
  idMax!: Number
  nomeFilter!: string
  filterArray: boolean[] = []
  shownUsers: number = 0

  loggedUser: User = JSON.parse(localStorage[LS_CHAVE])

  constructor(private projectService: ProjectService, public router: Router, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.fillProjects();
  }

  filter() {
    this.filterArray = []
    for (let project of this.projects) {
      console.log("out")
      console.log(project)
      console.log(this.nomeFilter)
      console.log("idMax:"+this.idMax + " . idMin:" + this.idMin + " . userId:" + project.line)
      //console.log("in")
      if (project.nome?.includes(this.nomeFilter) || this.nomeFilter == null) {
        if ((project.line! <= this.idMax && project.line! >= this.idMin) || this.idMax==null || this.idMin==null) {
          console.log("forthIf")
          //console.log(user)
          this.filterArray.push(true)
          continue
        }
      }
      this.filterArray.push(false)
    }
    console.log(this.filterArray)
    this.shownUsers = this.filterArray.filter(Boolean).length
  }

  fillProjects(): void {
    this.projectService.getAllProjects().subscribe({
      next: (data) => {
        this.projects = data//; this.ProjectsOriginal = JSON.parse(JSON.stringify(data))},
        this.filterArray = new Array(this.projects.length).fill(true)
        this.shownUsers = this.filterArray.filter(Boolean).length
      },
      error: (err) => console.log(err)
    });
  }

  enable(project: Project):void {
    let text = "Tem certeza que deseja reabilitar esse projeto?";
    if (confirm(text) == true) {
      project.enabled = true
      this.projectService.updateProject(project).subscribe({
        next: (data) => this.projects[project.line!] = project,
        error: (err) => console.log(err)
      });
    }
  }

  remove(project: Project): void {
    let text = "Tem certeza que deseja deletar esse projeto?";
    if (confirm(text) == true) {
      this.projectService.remove(project).subscribe({
        next: (data) => this.fillProjects(),
        error: (err) => console.log(err)
      });
    }
  }

  openModal(project: Project) {
    const modalRef = this.modalService.open(ModalProjectComponent)
    modalRef.componentInstance.project = project
    modalRef.componentInstance.projects = this.projects
    console.log("modal")
  }

  openAddModal() {
    const modalRef = this.modalService.open(ModalProjectComponent)
    modalRef.componentInstance.projects = this.projects
    modalRef.componentInstance.filterArray = this.filterArray
    modalRef.componentInstance.shownUsers = this.shownUsers
    modalRef.result.then(
      (result) => {
        this.shownUsers = this.filterArray.filter(Boolean).length
        //console.log(this.projects);
        //console.log(this.filterArray);
        //console.log(this.shownUsers);
      },
      (reason) => {
        //console.log(`Dismissed`);
      }
    );
  }
}
