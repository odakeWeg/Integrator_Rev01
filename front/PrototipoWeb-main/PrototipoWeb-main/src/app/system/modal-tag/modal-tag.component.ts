import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Routine, Project, Mapping, One } from 'src/app/shared';
import { BaseTag } from 'src/app/shared/models/tags/base-tag.model';
import { ProjectService } from '../services/project.service';

@Component({
  selector: 'app-modal-tag',
  templateUrl: './modal-tag.component.html',
  styleUrls: ['./modal-tag.component.css']
})
export class ModalTagComponent implements OnInit {
  @Input() tag!: BaseTag
  @Input() tags!: BaseTag[]
  @Input() routine!: Routine
  @Input() routines!: Routine[]
  @Input() project!: Project
  
  tagToUpdate!: BaseTag
  newTag!: BaseTag

  @ViewChild('formTag') formTag!: NgForm
  @ViewChild('formNewTag') formNewTag!: NgForm

  constructor(public activeModal: NgbActiveModal, private projectService: ProjectService) { }

  ngOnInit(): void {
    this.newTag = new BaseTag()
    this.initiateTagToUpdate()
  }

  updateProjectLocally() {
    for(let i = 0; i < this.routines.length; i++) {
      if(this.routines[i].line==this.routine.line) {
        this.routines[i] = this.routine
      }
    }
    this.project.routines = this.routines
  }

  updateTagLocally(tag: BaseTag) {
    for(let i = 0; i < this.tags.length; i++) {
      if(this.tags[i].id==tag.id) {
        this.tags[i] = tag
      }
    }
    this.routine.tag = this.tags
  }

  initiateTagToUpdate():void {
    this.tagToUpdate = new BaseTag()
    this.tagToUpdate = structuredClone(this.tag)
    if(this.tags==null) {
      this.tags = []
    }
    //this.mappingToUpdate = JSON.parse(JSON.stringify(this.mapping))
  }

  update():void {
    this.tag = this.formTag.value
    this.tag.enabled = true
    this.updateTagLocally(this.tag)
    this.updateProjectLocally()

    this.projectService.updateProject(this.project).subscribe({
      next: (data) => this.tags[this.tag.id!] = this.tag,
      error: (err) => console.log(err)
    });
    this.activeModal.close()
  }
  
  add():void {
    this.newTag = this.formNewTag.value
    this.newTag.enabled = true
    this.newTag.id = this.tags.length
    console.log(this.newTag)
    this.tags.push(this.newTag)
    this.routine.tag = this.tags
    this.updateProjectLocally()
    

    this.projectService.updateProject(this.project).subscribe({
      next: (data) => 0,//this.mappings.push(this.newMapping),
      error: (err) => console.log(err)
    });
    this.activeModal.close()
  }

  disable():void {
    this.tag.enabled = false
    this.updateTagLocally(this.tag)
    this.updateProjectLocally()
    this.projectService.updateProject(this.project).subscribe({
      next: (data) => this.tags[this.tag.id!] = this.tag,
      error: (err) => console.log(err)
    });
    this.activeModal.close()
  }
}