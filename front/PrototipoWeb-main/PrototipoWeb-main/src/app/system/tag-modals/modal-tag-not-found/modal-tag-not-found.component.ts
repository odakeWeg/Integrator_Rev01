import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-modal-tag-not-found',
  templateUrl: './modal-tag-not-found.component.html',
  styleUrls: ['./modal-tag-not-found.component.css']
})
export class ModalTagNotFoundComponent implements OnInit {

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
  }

}
