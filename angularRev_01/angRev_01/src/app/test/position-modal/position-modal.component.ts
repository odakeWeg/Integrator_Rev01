import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ProductLog } from 'src/app/models/models/product-log.model';
import { ResultLog } from 'src/app/models/models/result-log.model';

@Component({
  selector: 'app-position-modal',
  templateUrl: './position-modal.component.html',
  styleUrls: ['./position-modal.component.css']
})
export class PositionModalComponent implements OnInit {
  @Input() result!: ResultLog
  @Input() product!: ProductLog
  @Input() position!: number

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
  }

}
