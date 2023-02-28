import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Mapping, Project } from 'src/app/shared';
import { MappingService } from '../services/mapping.service';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ProjectService } from '../services/project.service';

@Component({
  selector: 'app-modal-mapping',
  templateUrl: './modal-mapping.component.html',
  styleUrls: ['./modal-mapping.component.css']
})
export class ModalMappingComponent implements OnInit {
  @Input() mapping!: Mapping
  @Input() mappings!: Mapping[]
  @Input() project!: Project
  
  mappingToUpdate!: Mapping
  newMapping!: Mapping

  @ViewChild('formMapping') formMapping!: NgForm
  @ViewChild('formNewMapping') formNewMapping!: NgForm

  constructor(public activeModal: NgbActiveModal, private projectService: ProjectService) { }

  ngOnInit(): void {
    console.log(this.mapping)
    this.newMapping = new Mapping()
    this.initiateMappingToUpdate()
  }

  updateProjectLocally() {
    //let maps = []
    //for(let i = 0; i < this.mappings.length; i++) {
    //  maps.push(JSON.stringify(this.mappings[i]))
    //}
    this.project.mappings = this.mappings
  }

  updateMapLocally(mapping: Mapping) {
    for(let i = 0; i < this.mappings.length; i++) {
      if(this.mappings[i].line==mapping.line) {
        this.mappings[i] = mapping
      }
    }
  }

  initiateMappingToUpdate():void {
    this.mappingToUpdate = new Mapping()
    this.mappingToUpdate = structuredClone(this.mapping)
    if(this.mappings==null) {
      this.mappings = []
    }
    //this.mappingToUpdate = JSON.parse(JSON.stringify(this.mapping))
  }

  update():void {
    let line = this.mapping.line
    this.mapping = this.formMapping.value
    this.mapping.enabled = true
    this.mapping.line = line
    this.updateMapLocally(this.mapping)
    this.updateProjectLocally()

    this.projectService.updateProject(this.project).subscribe({
      next: (data) => this.mappings[this.mapping.line!] = this.mapping,
      error: (err) => console.log(err)
    });
    this.activeModal.close()
  }
  
  add():void {
    this.newMapping = this.formNewMapping.value
    this.newMapping.enabled = true
    this.newMapping.line = this.mappings.length
    this.mappings.push(this.newMapping)
    //this.updateProjectLocally()
    this.project.mappings = this.mappings

    console.log(JSON.stringify(this.mappings))
    

    this.projectService.updateProject(this.project).subscribe({
      next: (data) => 0,//this.mappings.push(this.newMapping),
      error: (err) => console.log(err)
    });
    this.activeModal.close()
  }

  disable():void {
    this.mapping.enabled = false
    this.updateMapLocally(this.mapping)
    this.updateProjectLocally()
    this.projectService.updateProject(this.project).subscribe({
      next: (data) => this.mappings[this.mapping.line!] = this.mapping,
      error: (err) => console.log(err)
    });
    this.activeModal.close()
  }
}





/*
export class ModalMappingComponent implements OnInit {
  @Input() mapping!: Mapping
  @Input() mappings!: Mapping[]
  
  mappingToUpdate!: Mapping
  newMapping!: Mapping

  @ViewChild('formMapping') formMapping!: NgForm
  @ViewChild('formNewMapping') formNewMapping!: NgForm

  constructor(public activeModal: NgbActiveModal, private mappingService: MappingService) { }

  ngOnInit(): void {
    this.newMapping = new Mapping()
    this.initiateMappingToUpdate()
  }

  initiateMappingToUpdate():void {
    this.mappingToUpdate = new Mapping()
    this.mappingToUpdate = structuredClone(this.mapping)
    //this.mappingToUpdate = JSON.parse(JSON.stringify(this.mapping))
  }

  update():void {
    this.mapping = this.formMapping.value
    this.mappingService.updateMapping(this.mapping).subscribe({
      next: (data) => this.mappings[this.mapping.line!] = this.mapping,
      error: (err) => console.log(err)
    });
    this.activeModal.close()
  }
  
  add():void {
    this.newMapping = this.formNewMapping.value
    this.newMapping.enabled = true
    this.mappingService.addMapping(this.newMapping).subscribe({
      next: (data) => this.mappings.push(this.newMapping),
      error: (err) => console.log(err)
    });
    this.activeModal.close()
  }

  disable():void {
    this.mapping.enabled = false
    this.mappingService.updateMapping(this.mapping).subscribe({
      next: (data) => this.mappings[this.mapping.line!] = this.mapping,
      error: (err) => console.log(err)
    });
    this.activeModal.close()
  }
}
*/