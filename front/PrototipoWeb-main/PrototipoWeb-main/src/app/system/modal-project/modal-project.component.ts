import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Project } from 'src/app/shared';
import { ProjectService } from '../services/project.service';

@Component({
  selector: 'app-modal-project',
  templateUrl: './modal-project.component.html',
  styleUrls: ['./modal-project.component.css']
})
export class ModalProjectComponent implements OnInit {
  @Input() project!: Project
  @Input() projects!: Project[]
  @Input() filterArray!: boolean[]
  @Input() shownUsers!: number
  
  projectToUpdate!: Project
  newProject!: Project

  @ViewChild('formProject') formProject!: NgForm
  @ViewChild('formNewProject') formNewProject!: NgForm

  constructor(public activeModal: NgbActiveModal, private projectService: ProjectService) { }

  ngOnInit(): void {
    this.newProject = new Project()
    this.initiateProjectToUpdate()
  }

  initiateProjectToUpdate():void {
    this.projectToUpdate = new Project()
    this.projectToUpdate = structuredClone(this.project)
    //this.projectToUpdate = JSON.parse(JSON.stringify(this.project))
  }

  update():void {
    this.setProjectObj()
    this.projectService.updateProject(this.project).subscribe({
      next: (data) => this.projects[this.project.line!] = this.project,
      error: (err) => console.log(err)
    });
    this.activeModal.close()
  }

  setProjectObj():void {
    this.project.nome = this.formProject.value.nome
    this.project.node = this.formProject.value.node
    this.project.description = this.formProject.value.description
    this.project.enabled = true
    this.project.line = this.projectToUpdate.line
    console.log(this.formProject.value.keyWords)
    console.log(this.formProject.value.connections)
    try {
      this.project.keyWords = this.formProject.value.keyWords!.split(",")
    } catch (error) {
      //@Todo: refactor this: try catch only used to check if the statement is a array or string
    }

    try {
      this.project.connections = this.formProject.value.connections!.split(",").map((el: string) => {return Number(el)})
    } catch (error) {
      //@Todo: refactor this: try catch only used to check if the statement is a array or string
    }
  }
  
  add():void {
    this.setNewProjectObj()
    this.projectService.addProject(this.newProject).subscribe({
      next: (data) => {
        this.projects.push(this.newProject)
        //console.log(this.projects)
        //console.log("exists? " + this.filterArray)
        this.filterArray.push(true) //= new Array(this.projects.length).fill(true)
        //console.log(this.filterArray)
        //this.shownUsers = this.filterArray.filter(Boolean).length
        //console.log(this.filterArray.length)
        this.activeModal.close()
      },
      error: (err) => {
        console.log(err)
        this.activeModal.close()
      }
    });
    //this.activeModal.close()
  }

  setNewProjectObj():void {
    this.newProject = this.formNewProject.value
    this.newProject.enabled = true
    this.newProject.keyWords = this.formNewProject.value.keyWords!.split(",")
    this.newProject.connections = this.formNewProject.value.connections!.split(",").map((el: string) => {return Number(el)})
    this.newProject.line = this.projects.length

    //this.filterArray.push(true)
    //this.shownUsers = this.filterArray.length
  }

  disable():void {
    this.project.enabled = false
    this.projectService.updateProject(this.project).subscribe({
      next: (data) => this.projects[this.project.line!] = this.project,
      error: (err) => console.log(err)
    });
    this.activeModal.close("Ok")
  }
}
