import { LeafMultipleWriteTag } from './../../../shared/models/tags/leaf-multiple-write-tag.model';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BaseTag, Routine, Project, EnsTypeEnum, EnsVariableNameEnum, EnsTagConfiguration } from 'src/app/shared';
import { ProjectService } from '../../services/project.service';

@Component({
  selector: 'app-leaf-multiple-write-tag',
  templateUrl: './leaf-multiple-write-tag.component.html',
  styleUrls: ['./leaf-multiple-write-tag.component.css']
})
export class LeafMultipleWriteTagComponent implements OnInit {

  @Input() tag!: LeafMultipleWriteTag
  @Input() tags!: BaseTag[]
  @Input() routine!: Routine
  @Input() routines!: Routine[]
  @Input() project!: Project
  @Input() selectedTag!: string

  @Input() filterArray!: boolean[]
  @Input() shownUsers!: number
  
  tagToUpdate!: LeafMultipleWriteTag
  newTag!: LeafMultipleWriteTag
  enableFields!: boolean

  listOfEnsType: string[] = []
  listOfEnsVariableName: string[] = []

  @ViewChild('formTag') formTag!: NgForm
  @ViewChild('formNewTag') formNewTag!: NgForm

  constructor(public activeModal: NgbActiveModal, private projectService: ProjectService) { }

  ngOnInit(): void {
    this.fillListOfEnsType()
    this.fillListOfEnsVariableName()
    this.initiateNewTag()
    this.initiateTagToUpdate()
  }

  fillListOfEnsType(): void {
    for (const value in EnsTypeEnum) {
      //console.log(value)
      this.listOfEnsType.push(value)
    }
  }

  fillListOfEnsVariableName(): void {
    for (const value in EnsVariableNameEnum) {
      //console.log(value)
      this.listOfEnsVariableName.push(value)
    }
  }

  initiateNewTag(): void {
    this.newTag = new BaseTag()
    this.newTag.ensTagConfiguration = new EnsTagConfiguration()
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
      if(this.tags[i].line==tag.line) {
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
    let ensBuffer = this.tagToUpdate.ensTagConfiguration
    let line = this.tag.line
    this.tag = this.formTag.value
    this.tag.ensTagConfiguration = ensBuffer
    this.tag.enabled = true
    this.tag.line = line
    this.updateTagLocally(this.tag)
    this.updateProjectLocally()

    this.projectService.updateProject(this.project).subscribe({
      next: (data) => this.tags[this.tag.line!] = this.tag,
      error: (err) => console.log(err)
    });
    this.activeModal.close()
  }
  
  add():void {
    let ensBuffer = this.newTag.ensTagConfiguration
    this.newTag = this.formNewTag.value
    this.newTag.ensTagConfiguration = ensBuffer
    this.newTag.enabled = true
    this.newTag.line = this.tags.length
    this.tags.push(this.newTag)
    this.routine.tag = this.tags
    this.updateProjectLocally()
    

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
      next: (data) => this.tags[this.tag.line!] = this.tag,
      error: (err) => console.log(err)
    });
    this.activeModal.close()
  }

  addEnsType(): void {
    let inputElementAux = <HTMLInputElement>document.getElementById("ensTypeAux")
    let inputElement = <HTMLInputElement>document.getElementById("ensType")
    //console.log(inputElement.value)
    
    if (this.newTag.ensTagConfiguration!=null) {
      if(this.newTag.ensTagConfiguration.ensType==null) {
        this.newTag.ensTagConfiguration.ensType = [inputElementAux.value]
      } else {
        console.log(this.newTag.ensTagConfiguration.ensType)
        this.newTag.ensTagConfiguration.ensType.push(inputElementAux.value)
      }
      //this.formNewTag.value.ensType += this.newTag.ensTagConfiguration.ensType[0]
    }

    if(inputElement.value == "") {
      inputElement.value = inputElementAux.value
    } else {
      inputElement.value += "," + inputElementAux.value
    }
    
    console.log(this.formNewTag.value)
    console.log(this.newTag.ensTagConfiguration!)
    //console.log(this.newTag.ensTagConfiguration!.ensType![0])
    inputElementAux.value = ""
  }

  addEnsVariableName(): void {
    let inputElementAux = <HTMLInputElement>document.getElementById("ensVariableNameAux")
    let inputElement = <HTMLInputElement>document.getElementById("ensVariableName")
    //console.log(inputElement.value)
    
    if (this.newTag.ensTagConfiguration!=null) {
      if(this.newTag.ensTagConfiguration.ensVariableName==null) {
        this.newTag.ensTagConfiguration.ensVariableName = [inputElementAux.value]
      } else {
        console.log(this.newTag.ensTagConfiguration.ensVariableName)
        this.newTag.ensTagConfiguration.ensVariableName.push(inputElementAux.value)
      }
      //this.formNewTag.value.ensType += this.newTag.ensTagConfiguration.ensType[0]
    }

    if(inputElement.value == "") {
      inputElement.value = inputElementAux.value
    } else {
      inputElement.value += "," + inputElementAux.value
    }
    
    console.log(this.formNewTag.value)
    console.log(this.newTag.ensTagConfiguration!)
    //console.log(this.newTag.ensTagConfiguration!.ensType![0])
    inputElementAux.value = ""
  }

  addVariableToReadFrom(): void {
    let inputElementAux = <HTMLInputElement>document.getElementById("variableToReadFromAux")
    let inputElement = <HTMLInputElement>document.getElementById("variableToReadFrom")
    //console.log(inputElement.value)
    
    if (this.newTag.ensTagConfiguration!=null) {
      if(this.newTag.ensTagConfiguration.variableToReadFrom==null) {
        this.newTag.ensTagConfiguration.variableToReadFrom = [inputElementAux.value]
      } else {
        console.log(this.newTag.ensTagConfiguration.variableToReadFrom)
        this.newTag.ensTagConfiguration.variableToReadFrom.push(inputElementAux.value)
      }
      //this.formNewTag.value.ensType += this.newTag.ensTagConfiguration.ensType[0]
    }

    if(inputElement.value == "") {
      inputElement.value = inputElementAux.value
    } else {
      inputElement.value += "," + inputElementAux.value
    }
    
    console.log(this.formNewTag.value)
    console.log(this.newTag.ensTagConfiguration!)
    //console.log(this.newTag.ensTagConfiguration!.ensType![0])
    inputElementAux.value = ""
  }
  
  ensChecker(): void {
    if(this.newTag.ensTagConfiguration?.enabled) {
      document.getElementById('ensTypeAux')?.removeAttribute("disabled");
      document.getElementById('ensVariableNameAux')?.removeAttribute("disabled");
      document.getElementById('variableToReadFromAux')?.removeAttribute("disabled");
      //document.getElementById('ensType')?.removeAttribute("disabled");
      //document.getElementById('ensVariableName')?.removeAttribute("disabled");
      //document.getElementById('variableToReadFrom')?.removeAttribute("disabled");
    } else {
      //this.newTag.ensTagConfiguration = new EnsTagConfiguration()
      document.getElementById('ensTypeAux')?.setAttribute("disabled", "disabled")
      document.getElementById('ensVariableNameAux')?.setAttribute("disabled", "disabled")
      document.getElementById('variableToReadFromAux')?.setAttribute("disabled", "disabled")
      //document.getElementById('ensType')?.setAttribute("disabled", "disabled")
      //document.getElementById('ensVariableName')?.setAttribute("disabled", "disabled")
      //document.getElementById('variableToReadFrom')?.setAttribute("disabled", "disabled")
    }
  }

  //Update

  addUpdateEnsType(): void {
    let inputElementAux = <HTMLInputElement>document.getElementById("ensTypeAux")
    let inputElement = <HTMLInputElement>document.getElementById("ensType")
    //console.log(inputElement.value)
    
    if (this.tagToUpdate.ensTagConfiguration!=null) {
      if(this.tagToUpdate.ensTagConfiguration.ensType==null) {
        this.tagToUpdate.ensTagConfiguration.ensType = [inputElementAux.value]
      } else {
        console.log(this.tagToUpdate.ensTagConfiguration.ensType)
        this.tagToUpdate.ensTagConfiguration.ensType.push(inputElementAux.value)
      }
      //this.formNewTag.value.ensType += this.newTag.ensTagConfiguration.ensType[0]
    }

    if(inputElement.value == "") {
      inputElement.value = inputElementAux.value
    } else {
      inputElement.value += "," + inputElementAux.value
    }
    
    console.log(this.formTag.value)
    console.log(this.tagToUpdate.ensTagConfiguration!)
    //console.log(this.newTag.ensTagConfiguration!.ensType![0])
    inputElementAux.value = ""
  }

  addUpdateEnsVariableName(): void {
    let inputElementAux = <HTMLInputElement>document.getElementById("ensVariableNameAux")
    let inputElement = <HTMLInputElement>document.getElementById("ensVariableName")
    //console.log(inputElement.value)
    
    if (this.tagToUpdate.ensTagConfiguration!=null) {
      if(this.tagToUpdate.ensTagConfiguration.ensVariableName==null) {
        this.tagToUpdate.ensTagConfiguration.ensVariableName = [inputElementAux.value]
      } else {
        console.log(this.tagToUpdate.ensTagConfiguration.ensVariableName)
        this.tagToUpdate.ensTagConfiguration.ensVariableName.push(inputElementAux.value)
      }
      //this.formNewTag.value.ensType += this.newTag.ensTagConfiguration.ensType[0]
    }

    if(inputElement.value == "") {
      inputElement.value = inputElementAux.value
    } else {
      inputElement.value += "," + inputElementAux.value
    }
    
    console.log(this.formTag.value)
    console.log(this.tagToUpdate.ensTagConfiguration!)
    //console.log(this.newTag.ensTagConfiguration!.ensType![0])
    inputElementAux.value = ""
  }

  addUpdateVariableToReadFrom(): void {
    let inputElementAux = <HTMLInputElement>document.getElementById("variableToReadFromAux")
    let inputElement = <HTMLInputElement>document.getElementById("variableToReadFrom")
    //console.log(inputElement.value)
    
    if (this.tagToUpdate.ensTagConfiguration!=null) {
      if(this.tagToUpdate.ensTagConfiguration.variableToReadFrom==null) {
        this.tagToUpdate.ensTagConfiguration.variableToReadFrom = [inputElementAux.value]
      } else {
        console.log(this.tagToUpdate.ensTagConfiguration.variableToReadFrom)
        this.tagToUpdate.ensTagConfiguration.variableToReadFrom.push(inputElementAux.value)
      }
      //this.formNewTag.value.ensType += this.newTag.ensTagConfiguration.ensType[0]
    }

    if(inputElement.value == "") {
      inputElement.value = inputElementAux.value
    } else {
      inputElement.value += "," + inputElementAux.value
    }
    
    console.log(this.formTag.value)
    console.log(this.tagToUpdate.ensTagConfiguration!)
    //console.log(this.newTag.ensTagConfiguration!.ensType![0])
    inputElementAux.value = ""
  }

  ensUpdateChecker(): void {
    if(this.tagToUpdate.ensTagConfiguration?.enabled) {
      document.getElementById('ensTypeAux')?.removeAttribute("disabled");
      document.getElementById('ensVariableNameAux')?.removeAttribute("disabled");
      document.getElementById('variableToReadFromAux')?.removeAttribute("disabled");
      //document.getElementById('ensType')?.removeAttribute("disabled");
      //document.getElementById('ensVariableName')?.removeAttribute("disabled");
      //document.getElementById('variableToReadFrom')?.removeAttribute("disabled");
    } else {
      //this.newTag.ensTagConfiguration = new EnsTagConfiguration()
      document.getElementById('ensTypeAux')?.setAttribute("disabled", "disabled")
      document.getElementById('ensVariableNameAux')?.setAttribute("disabled", "disabled")
      document.getElementById('variableToReadFromAux')?.setAttribute("disabled", "disabled")
      //document.getElementById('ensType')?.setAttribute("disabled", "disabled")
      //document.getElementById('ensVariableName')?.setAttribute("disabled", "disabled")
      //document.getElementById('variableToReadFrom')?.setAttribute("disabled", "disabled")
    }
  }
}