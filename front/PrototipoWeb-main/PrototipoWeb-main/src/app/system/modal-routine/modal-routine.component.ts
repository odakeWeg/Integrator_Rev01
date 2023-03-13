import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Mapping, Project, Routine } from 'src/app/shared';
import { ProjectService } from '../services/project.service';

@Component({
  selector: 'app-modal-routine',
  templateUrl: './modal-routine.component.html',
  styleUrls: ['./modal-routine.component.css']
})
export class ModalRoutineComponent implements OnInit {
  @Input() routine!: Routine
  @Input() routines!: Routine[]
  @Input() project!: Project
  @Input() filterArray!: boolean[]
  @Input() shownUsers!: number
  
  routineToUpdate!: Routine
  newRoutine!: Routine

  @ViewChild('formRoutine') formRoutine!: NgForm
  @ViewChild('formNewRoutine') formNewRoutine!: NgForm

  constructor(public activeModal: NgbActiveModal, private projectService: ProjectService) { }

  ngOnInit(): void {
    this.newRoutine = new Routine()
    this.initiateRoutineToUpdate()
  }

  updateProjectLocally() {
    //let maps = []
    //for(let i = 0; i < this.mappings.length; i++) {
    //  maps.push(JSON.stringify(this.mappings[i]))
    //}
    this.project.routines = this.routines
  }

  updateRoutineLocally(routine: Routine) {
    for(let i = 0; i < this.routines.length; i++) {
      if(this.routines[i].line==routine.line) {
        this.routines[i] = routine
      }
    }
  }

  initiateRoutineToUpdate():void {
    this.routineToUpdate = new Routine()
    this.routineToUpdate = structuredClone(this.routine)
    if(this.routines==null) {
      this.routines = []
    }
    //this.mappingToUpdate = JSON.parse(JSON.stringify(this.mapping))
  }

  update():void {
    let line = this.routine.line
    let tagList = this.routine.tag
    this.routine = this.formRoutine.value
    this.routine.tag = tagList
    this.routine.enabled = true
    this.routine.line = line
    this.updateRoutineLocally(this.routine)
    this.updateProjectLocally()

    this.projectService.updateProject(this.project).subscribe({
      next: (data) => this.routines[this.routine.line!] = this.routine,
      error: (err) => console.log(err)
    });
    this.activeModal.close()
  }
  
  add():void {
    this.newRoutine = this.formNewRoutine.value
    this.newRoutine.enabled = true
    this.newRoutine.tag = []
    this.newRoutine.line = this.routines.length
    this.routines.push(this.newRoutine)
    //this.updateProjectLocally()
    this.project.routines = this.routines

    console.log(JSON.stringify(this.routines))
    

    this.projectService.updateProject(this.project).subscribe({
      next: (data) => {
        this.filterArray.push(true)//this.mappings.push(this.newMapping),
        this.activeModal.close()
      },
      error: (err) => {
        console.log(err)
        this.activeModal.close()
      }
    });
    //this.activeModal.close()
  }

  disable():void {
    this.routine.enabled = false
    this.updateRoutineLocally(this.routine)
    this.updateProjectLocally()
    this.projectService.updateProject(this.project).subscribe({
      next: (data) => this.routines[this.routine.line!] = this.routine,
      error: (err) => console.log(err)
    });
    this.activeModal.close()
  }
}