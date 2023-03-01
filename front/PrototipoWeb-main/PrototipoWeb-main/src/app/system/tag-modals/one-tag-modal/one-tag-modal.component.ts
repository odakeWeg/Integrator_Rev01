import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BaseTag, One, Project, Routine } from 'src/app/shared';
import { ProjectService } from '../../services/project.service';

@Component({
  selector: 'app-one-tag-modal',
  templateUrl: './one-tag-modal.component.html',
  styleUrls: ['./one-tag-modal.component.css']
})
export class OneTagModalComponent implements OnInit {
  @Input() tag!: One
  @Input() tags!: BaseTag[]
  @Input() routine!: Routine
  @Input() routines!: Routine[]
  @Input() project!: Project
  @Input() selectedTag!: string

  @Input() filterArray!: boolean[]
  @Input() shownUsers!: number
  
  tagToUpdate!: One
  newTag!: One
  enableFields!: boolean



  @ViewChild('formTag') formTag!: NgForm
  @ViewChild('formNewTag') formNewTag!: NgForm

  constructor(public activeModal: NgbActiveModal, private projectService: ProjectService) { }

  ngOnInit(): void {
    this.initiateNewTag()
    this.initiateTagToUpdate()
  }

  initiateNewTag(): void {
    this.newTag = new BaseTag()
    this.newTag.nome = this.selectedTag
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
    if(this.tagToUpdate.registerName!="") {
      document.getElementById('register')?.setAttribute("disabled", "disabled")
    }
    //this.mappingToUpdate = JSON.parse(JSON.stringify(this.mapping))
  }

  update():void {
    let id = this.tag.id
    this.tag = this.formTag.value
    this.tag.enabled = true
    this.tag.id = id
    this.updateTagLocally(this.tag)
    this.updateProjectLocally()

    console.log(this.project)
    this.projectService.updateProject(this.project).subscribe({
      next: (data) => this.tags[this.tag.id!] = this.tag,
      error: (err) => console.log(err)
    });
    this.activeModal.close()
  }
  
  add():void {
    console.log(this.formNewTag.value)
    this.newTag = this.formNewTag.value
    this.newTag.enabled = true
    this.newTag.id = this.tags.length
    this.tags.push(this.newTag)
    this.routine.tag = this.tags
    this.updateProjectLocally()
    
    console.log(this.project)
    this.projectService.updateProject(this.project).subscribe({
      next: (data) => {
        this.filterArray.push(true),//this.mappings.push(this.newMapping),
        this.activeModal.close()
      },
      error: (err) => {
        console.log(err)
        this.activeModal.close()
      }
    });
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

  setRegister():void {
    if(this.tagToUpdate.registerName!="") {
      for(let mapping of this.project.mappings!) {
        if(this.tagToUpdate.registerName==mapping.nome) {
          this.tagToUpdate.register = mapping.mapping
        }
      }
      document.getElementById('register')?.setAttribute("disabled", "disabled")
    } else {
      this.tagToUpdate.register = ""
      document.getElementById('register')?.removeAttribute("disabled");
    }
  }

  setNewRegister():void {
    if(this.newTag.registerName!="") {
      for(let mapping of this.project.mappings!) {
        if(this.newTag.registerName==mapping.nome) {
          this.newTag.register = mapping.mapping
        }
      }
      document.getElementById('newRegister')?.setAttribute("disabled", "disabled")
    } else {
      this.newTag.register = ""
      document.getElementById('newRegister')?.removeAttribute("disabled");
    }
  }

  /*
  setMap():void {
    this.project.mappings
  }
  */
}