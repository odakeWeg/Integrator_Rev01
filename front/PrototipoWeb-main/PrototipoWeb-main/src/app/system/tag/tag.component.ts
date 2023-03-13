import { LeafInlineSetupTagComponent } from './../tag-modals/leaf-inline-setup-tag/leaf-inline-setup-tag.component';
import { LeafVerifyTagComponent } from './../tag-modals/leaf-verify-tag/leaf-verify-tag.component';
import { LeafEthernetCommunicationTagComponent } from './../tag-modals/leaf-ethernet-communication-tag/leaf-ethernet-communication-tag.component';
import { LeafEnsSetupTagComponent } from './../tag-modals/leaf-ens-setup-tag/leaf-ens-setup-tag.component';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Routine, Project, TagEnum, One, User } from 'src/app/shared';
import { BaseTag } from 'src/app/shared/models/tags/base-tag.model';
import { ModalRoutineComponent } from '../modal-routine/modal-routine.component';
import { ModalTagComponent } from '../modal-tag/modal-tag.component';
import { ProjectService } from '../services/project.service';
import { ModalTagNotFoundComponent } from '../tag-modals/modal-tag-not-found/modal-tag-not-found.component';
import { OneTagModalComponent } from '../tag-modals/one-tag-modal/one-tag-modal.component';
import { LeafIOLinkCommunicationTagComponent } from '../tag-modals/leaf-iolink-communication-tag/leaf-iolink-communication-tag.component';
import { LeafModbusCommunicationTagComponent } from '../tag-modals/leaf-modbus-communication-tag/leaf-modbus-communication-tag.component';
import { LeafMultipleRegisterCompareTagComponent } from '../tag-modals/leaf-multiple-register-compare-tag/leaf-multiple-register-compare-tag.component';
import { LeafMultipleWriteTagComponent } from '../tag-modals/leaf-multiple-write-tag/leaf-multiple-write-tag.component';
import { LeafRegisterCompareTagComponent } from '../tag-modals/leaf-register-compare-tag/leaf-register-compare-tag.component';
import { LeafStringWriteTagComponent } from '../tag-modals/leaf-string-write-tag/leaf-string-write-tag.component';
import { LeafTestTagComponent } from '../tag-modals/leaf-test-tag/leaf-test-tag.component';
import { LeafUserConfirmationTagComponent } from '../tag-modals/leaf-user-confirmation-tag/leaf-user-confirmation-tag.component';
import { LeafUserInputTagComponent } from '../tag-modals/leaf-user-input-tag/leaf-user-input-tag.component';
import { LeafVariableCompareTagComponent } from '../tag-modals/leaf-variable-compare-tag/leaf-variable-compare-tag.component';
import { LeafVariableStringWriteTagComponent } from '../tag-modals/leaf-variable-string-write-tag/leaf-variable-string-write-tag.component';
import { LeafVariableWriteTagComponent } from '../tag-modals/leaf-variable-write-tag/leaf-variable-write-tag.component';
import { LeafWriteTagComponent } from '../tag-modals/leaf-write-tag/leaf-write-tag.component';

const LS_CHAVE: string = "userSession";

@Component({
  selector: 'app-tag',
  templateUrl: './tag.component.html',
  styleUrls: ['./tag.component.css']
})
export class TagComponent implements OnInit {
  selectedTag: string = ""
  listOfTagName: string[] = []
  tags: BaseTag[] = []
  routines: Routine[] = []
  routine: Routine = new Routine()
  project: Project = new Project()

  idMin!: Number
  idMax!: Number
  nomeFilter!: string
  positionFilter!: number
  filterArray: boolean[] = []
  shownUsers: number = 0

  loggedUser: User = JSON.parse(localStorage[LS_CHAVE])

  constructor(private projectService: ProjectService, public router: Router, private activatedRoute: ActivatedRoute, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.fillTags();
    this.fillListOfTagName();
    console.log(this.project)
    console.log(this.routines)
    console.log(this.routine)
    console.log(this.tags)
  }

  filter() {
    this.filterArray = []
    for (let tag of this.tags) {
      console.log("out")
      console.log(tag)
      console.log(this.nomeFilter)
      console.log("idMax:"+this.idMax + " . idMin:" + this.idMin + " . userId:" + tag.id)
      //console.log("in")
      if (tag.tagName?.includes(this.nomeFilter) || this.nomeFilter == null) {
        if(tag.position==this.positionFilter || this.positionFilter == null) {
          if ((tag.id! <= this.idMax && tag.id! >= this.idMin) || this.idMax==null || this.idMin==null) {
            console.log("forthIf")
            //console.log(user)
            this.filterArray.push(true)
            continue
          }
        }
      }
      this.filterArray.push(false)
    }
    console.log(this.filterArray)
    this.shownUsers = this.filterArray.filter(Boolean).length
  }

  fillListOfTagName(): void {
    for (const value in TagEnum) {
      //console.log(value)
      this.listOfTagName.push(value)
    }
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

  setRetrievedValues(data: Project[]):void {
    this.project = data[0]
    this.routines = data[0].routines!
    this.routine = this.routines[Number(this.activatedRoute.snapshot.paramMap.get('routineLine')!)]!
    if(this.routine.tag!=null) {
      this.tags = this.routine.tag
    }
    this.filterArray = new Array(this.tags.length).fill(true)
    this.shownUsers = this.filterArray.filter(Boolean).length
  }

  fillTags(): void {
    this.projectService.getProjectByLine(this.activatedRoute.snapshot.paramMap.get('line')!).subscribe({
      next: (data) => {
        console.log(data)
        this.setRetrievedValues(data)
      },
      error: (err) => console.log(err)
    });
  }

  enable(tag: BaseTag):void {
    tag.enabled = true
    this.updateTagLocally(tag)
    this.updateProjectLocally()

    this.projectService.updateProject(this.project).subscribe({
      next: (data) => this.tags[tag.id!] = tag,
      error: (err) => console.log(err)
    });
  }

  orderTagsByArrayAsc(): void {
    for(let i = 0; i < this.tags.length; i++) {
      this.tags[i].id = i
    }
  }

  remove(tag: BaseTag): void {
    //let text = "Tem certeza que deseja deletar essa tag?";
    //if (confirm(text) == true) {
      this.tags.splice(tag.id!, 1)
      this.orderTagsByArrayAsc()
      this.updateProjectLocally()
      
      this.projectService.updateProject(this.project).subscribe({
        next: (data) => {
          //this.mappings[mapping.line!] = mapping,
          this.fillTags()
        },
        error: (err) => console.log(err)
      });
    //}
  }

  /*
  for (const value in TagEnum) {
      this.listOfTagName.push(value)
    }
  */

  openModal(tag: BaseTag) {
    const modalRef = this.modalService.open(this.getTagName(tag.tagName!))
    //let modalRef
    //eval("modalRef = this.modalService.open(" + tag.nome + "Component)")
    //eval("modalRef = this.modalService.open(ModalTagComponent)")
    modalRef.componentInstance.tag = tag
    modalRef.componentInstance.tags = this.tags
    modalRef.componentInstance.routine = this.routine
    modalRef.componentInstance.routines = this.routines
    modalRef.componentInstance.project = this.project
  }

  openAddModal() {
    //if (this.selectedTag!="") {
      const modalRef = this.modalService.open(this.getTagName(this.selectedTag))
      //let modalRef = this.modalService.open(null)
      //eval("modalRef = this.modalService.open(" + this.selectedTag + "Component)")
      modalRef.componentInstance.selectedTag = this.selectedTag
      modalRef.componentInstance.tags = this.tags
      modalRef.componentInstance.routine = this.routine
      modalRef.componentInstance.routines = this.routines
      modalRef.componentInstance.project = this.project
      
      modalRef.componentInstance.filterArray = this.filterArray
      modalRef.componentInstance.shownUsers = this.shownUsers
      modalRef.result.then(
        (result) => {

          //console.log(this.filterArray)
          this.shownUsers = this.filterArray.filter(Boolean).length
          //console.log(this.shownUsers)
          //console.log(this.projects);
          //console.log(this.filterArray);
          //console.log(this.shownUsers);
        },
        (reason) => {
          //console.log(`Dismissed`);
        }
      );
    //} else {
    //  alert("Choose a Tag Type")
    //}
  }

  dragBufferTags: BaseTag[] = []
  lastDraggedId: number = 0
  onDragStart(event: DragEvent) {
    //1) Copiar array
    //2) Copiar id do selecionado
    let div = <HTMLDivElement>event.target
    this.dragBufferTags = JSON.parse(JSON.stringify(this.tags))
    this.lastDraggedId = Number(this.getStringBetween(div.innerHTML, "Id: ", "</"))
    //let div = <HTMLDivElement>event.target
    //console.log(div.innerHTML)
    //console.log(this.getStringBetween(div.innerHTML, "Id: ", "</"))
    
  }
  
  onDragEnd(event: DragEvent) {
    //1) Jogar alerta
    //2) Trocar array ou não
    //let div = <HTMLDivElement>event.target
    //console.log(div.innerHTML)
    //console.log(this.getStringBetween(div.innerHTML, "Id: ", "</"))
    let text = "Tem certeza que deseja alterar a posição da tag?";
    if (confirm(text) == true) {
      this.project.routines![this.routine.line!].tag = this.tags
      this.projectService.updateProject(this.project).subscribe({
        next: (data) => {
          //this.mappings[mapping.line!] = mapping,
          this.fillTags()
        },
        error: (err) => console.log(err)
      });
    } else {
      this.tags = this.dragBufferTags
    }
  }

  onDragLeave(event: DragEvent) {
    //@Todo: Atualização de posição enquanto faz o hover do draggable
    let div = <HTMLDivElement>event.target
    //console.log(div.innerHTML)
    try {
      //console.log(Number(this.getStringBetween(div.innerHTML, "Id: ", "</")))
      let nextDraggedId = Number(this.getStringBetween(div.innerHTML, "Id: ", "</"))
      this.tags = this.swapArrayElements(this.tags, this.lastDraggedId, nextDraggedId)
      this.lastDraggedId = nextDraggedId
    } catch (e) {
      //console.log("failed")
    }
    
    
  }

  swapArrayElements(array: BaseTag[], indexOne: number, indexTwo: number): any {
    //@Todo: there is a nonConsecutive problem, if i drag outside de document div it just swap positions
    let buffer = array[indexOne];
    array[indexOne] = array[indexTwo];
    array[indexOne].id = indexOne
    array[indexTwo] = buffer;
    array[indexTwo].id = indexTwo
    return array
  }

  getStringBetween(str: string, start: string, end: string): string {
    const result = str.match(new RegExp(start + "(.*)" + end));

    return result![1].split("<")[0];
  }



  getTagName(tagName: string): any{ //@Todo: Make it better or at least group some functions to override
    console.log(tagName)
    switch (tagName) {
      case TagEnum.LeafEnsSetupTag:
        return LeafEnsSetupTagComponent
      case TagEnum.LeafEthernetCommunicationTag:
        return LeafEthernetCommunicationTagComponent
      case TagEnum.LeafIOLinkCommunicationTag:
        return LeafIOLinkCommunicationTagComponent
      case TagEnum.LeafModbusCommunicationTag:
        return LeafModbusCommunicationTagComponent
      case TagEnum.LeafMultipleRegisterCompareTag:
        return LeafMultipleRegisterCompareTagComponent
      case TagEnum.LeafMultipleWriteTag:
        return LeafMultipleWriteTagComponent
      case TagEnum.LeafRegisterCompareTag:
        return LeafRegisterCompareTagComponent
      case TagEnum.LeafStringWriteTag:
        return LeafStringWriteTagComponent
      case TagEnum.LeafTestTag:
        return LeafTestTagComponent
      case TagEnum.LeafUserConfirmationTag:
        return LeafUserConfirmationTagComponent
      case TagEnum.LeafUserInputTag:
        return LeafUserInputTagComponent
      case TagEnum.LeafVariableCompareTag:
        return LeafVariableCompareTagComponent
      case TagEnum.LeafVariableStringWriteTag:
        return LeafVariableStringWriteTagComponent
      case TagEnum.LeafVariableWriteTag:
        return LeafVariableWriteTagComponent
      case TagEnum.LeafWriteTag:
        return LeafWriteTagComponent
      case TagEnum.LeafVerifyTag:
        return LeafVerifyTagComponent
      case TagEnum.LeafInlineSetupTag:
        return LeafInlineSetupTagComponent
    }
    return ModalTagNotFoundComponent
  }
}