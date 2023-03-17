import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Project, Routine, User } from 'src/app/shared';
import { ModalProjectComponent } from '../modal-project/modal-project.component';
import { ProjectService } from '../services/project.service';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalRoutineComponent } from '../modal-routine/modal-routine.component';

//const LS_CHAVE: string = "userSession";

@Component({
  selector: 'app-routine',
  templateUrl: './routine.component.html',
  styleUrls: ['./routine.component.css']
})

export class RoutineComponent implements OnInit {
  routines: Routine[] = []
  project: Project = new Project()

  idMin!: Number
  idMax!: Number
  nomeFilter!: string
  filterArray: boolean[] = []
  shownUsers: number = 0

  loggedUser: User = new User()//JSON.parse(localStorage[LS_CHAVE])
  LS_CHAVE: string = "userSession";

  constructor(private projectService: ProjectService, public router: Router, private activatedRoute: ActivatedRoute, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.fillRoutines();
    this.verifyIfLogged()
  }

  verifyIfLogged(): void {
    //@Todo: LoginPage for this software
    if(localStorage[this.LS_CHAVE]!=null) {
      this.loggedUser = JSON.parse(localStorage[this.LS_CHAVE])
    } else {
      this.loggedUser.id = 0
      this.loggedUser.line = 0
      this.loggedUser.nome = "Edson"
      this.loggedUser.cadastro = 7881
      this.loggedUser.perfil = "Adm"
      //this.router.navigateByUrl('/login')
    }
  }

  filter() {
    this.filterArray = []
    for (let routine of this.routines) {
      console.log("out")
      console.log(routine)
      console.log(this.nomeFilter)
      console.log("idMax:"+this.idMax + " . idMin:" + this.idMin + " . userId:" + routine.line)
      //console.log("in")
      if (routine.nome?.includes(this.nomeFilter) || this.nomeFilter == null) {
        if ((routine.line! <= this.idMax && routine.line! >= this.idMin) || this.idMax==null || this.idMin==null) {
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

  fillRoutines(): void {
    this.projectService.getProjectByLine(this.activatedRoute.snapshot.paramMap.get('line')!).subscribe({
      next: (data) => {
        this.project = data[0]
        if(data[0].routines!=null) {
          this.routines = data[0].routines//this.stringToObj(data.mappings!)
        }
        this.filterArray = new Array(this.routines.length).fill(true)
        this.shownUsers = this.filterArray.filter(Boolean).length
      },
      error: (err) => console.log(err)
    });
  }

  enable(routine: Routine):void {
    routine.enabled = true
    this.updateRoutineLocally(routine)
    this.updateProjectLocally()

    this.projectService.updateProject(this.project).subscribe({
      next: (data) => this.routines[routine.line!] = routine,
      error: (err) => console.log(err)
    });
  }

  orderRoutinesByArrayAsc(): void {
    for(let i = 0; i < this.routines.length; i++) {
      this.routines[i].line = i
    }
  }

  remove(routine: Routine): void {
    let text = "Tem certeza que deseja deletar essa rotina?";
    if (confirm(text) == true) {
      this.routines.splice(routine.line!, 1)
      this.orderRoutinesByArrayAsc()
      this.updateProjectLocally()
      
      this.projectService.updateProject(this.project).subscribe({
        next: (data) => this.fillRoutines(),//this.mappings[mapping.line!] = mapping,
        error: (err) => console.log(err)
      });
    }
  }

  openModal(routine: Routine) {
    const modalRef = this.modalService.open(ModalRoutineComponent)
    modalRef.componentInstance.routine = routine
    modalRef.componentInstance.routines = this.routines
    modalRef.componentInstance.project = this.project
  }

  openAddModal() {
    const modalRef = this.modalService.open(ModalRoutineComponent)
    modalRef.componentInstance.routines = this.routines
    modalRef.componentInstance.project = this.project
    modalRef.componentInstance.filterArray = this.filterArray
    modalRef.componentInstance.shownUsers = this.shownUsers
    modalRef.result.then(
      (result) => {

        console.log(this.filterArray)
        this.shownUsers = this.filterArray.filter(Boolean).length
        console.log(this.shownUsers)
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




/*
export class RoutineComponent implements OnInit {
  projects!: Project[]

  constructor(private projectService: ProjectService, public router: Router, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.fillProjects();
  }

  fillProjects(): void {
    this.projectService.getAllProjects().subscribe({
      next: (data) => this.projects = data,//; this.ProjectsOriginal = JSON.parse(JSON.stringify(data))},
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
}
*/