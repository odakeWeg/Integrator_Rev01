import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TestInitializerComponent } from './test-initializer/test-initializer.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { InputModalComponent } from './modals/input-modal/input-modal.component';
import { ConfirmationModalComponent } from './modals/confirmation-modal/confirmation-modal.component';



@NgModule({
  declarations: [
    TestInitializerComponent,
    InputModalComponent,
    ConfirmationModalComponent
  ],
  exports: [
    TestInitializerComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class AutomaticTestModule { }
