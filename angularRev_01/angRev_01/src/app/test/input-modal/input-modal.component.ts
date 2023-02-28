import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-input-modal',
  templateUrl: './input-modal.component.html',
  styleUrls: ['./input-modal.component.css']
})
export class InputModalComponent implements OnInit {
  @Input() message!: string
  @Input() userInput!: string[]

  inputBuffer!: string

  @ViewChild('formSend') formSend!: NgForm

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
  }

  send(): void {
    this.userInput.push(this.inputBuffer)
    this.activeModal.close()
  }

}
