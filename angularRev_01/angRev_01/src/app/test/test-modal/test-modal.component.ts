import { ResultLog } from './../../models/models/result-log.model';
import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-test-modal',
  templateUrl: './test-modal.component.html',
  styleUrls: ['./test-modal.component.css']
})
export class TestModalComponent implements OnInit {
  @Input() result!: ResultLog

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
  }

  

}
