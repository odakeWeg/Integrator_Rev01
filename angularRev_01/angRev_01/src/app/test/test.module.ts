import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TestInitializerComponent } from './test-initializer/test-initializer.component';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TestModalComponent } from './test-modal/test-modal.component';
import { TestInitializerV2Component } from './test-initializer-v2/test-initializer-v2.component';
import { TestInitializerV3Component } from './test-initializer-v3/test-initializer-v3.component';



@NgModule({
  declarations: [
    TestInitializerComponent,
    TestModalComponent,
    TestInitializerV2Component,
    TestInitializerV3Component
  ],
  exports: [
    TestInitializerComponent,
    TestModalComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class TestModule { }
