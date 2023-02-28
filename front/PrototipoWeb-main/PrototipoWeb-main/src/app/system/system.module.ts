import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MappingComponent } from './mapping/mapping.component';
import { RoutineComponent } from './routine/routine.component';
import { ProjectComponent } from './project/project.component';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { ModalProjectComponent } from './modal-project/modal-project.component';
import { ProjectFolderComponent } from './project-folder/project-folder.component';
import { ModalMappingComponent } from './modal-mapping/modal-mapping.component';
import { ModalRoutineComponent } from './modal-routine/modal-routine.component';
import { TagComponent } from './tag/tag.component';
import { ModalTagComponent } from './modal-tag/modal-tag.component';
import { OneTagModalComponent } from './tag-modals/one-tag-modal/one-tag-modal.component';
import { LeafEnsSetupTagComponent } from './tag-modals/leaf-ens-setup-tag/leaf-ens-setup-tag.component';
import { LeafEthernetCommunicationTagComponent } from './tag-modals/leaf-ethernet-communication-tag/leaf-ethernet-communication-tag.component';
import { LeafIOLinkCommunicationTagComponent } from './tag-modals/leaf-iolink-communication-tag/leaf-iolink-communication-tag.component';
import { LeafModbusCommunicationTagComponent } from './tag-modals/leaf-modbus-communication-tag/leaf-modbus-communication-tag.component';
import { LeafMultipleRegisterCompareTagComponent } from './tag-modals/leaf-multiple-register-compare-tag/leaf-multiple-register-compare-tag.component';
import { LeafMultipleWriteTagComponent } from './tag-modals/leaf-multiple-write-tag/leaf-multiple-write-tag.component';
import { LeafRegisterCompareTagComponent } from './tag-modals/leaf-register-compare-tag/leaf-register-compare-tag.component';
import { LeafStringWriteTagComponent } from './tag-modals/leaf-string-write-tag/leaf-string-write-tag.component';
import { LeafTestTagComponent } from './tag-modals/leaf-test-tag/leaf-test-tag.component';
import { LeafUserConfirmationTagComponent } from './tag-modals/leaf-user-confirmation-tag/leaf-user-confirmation-tag.component';
import { LeafUserInputTagComponent } from './tag-modals/leaf-user-input-tag/leaf-user-input-tag.component';
import { LeafVariableCompareTagComponent } from './tag-modals/leaf-variable-compare-tag/leaf-variable-compare-tag.component';
import { LeafVariableStringWriteTagComponent } from './tag-modals/leaf-variable-string-write-tag/leaf-variable-string-write-tag.component';
import { LeafVariableWriteTagComponent } from './tag-modals/leaf-variable-write-tag/leaf-variable-write-tag.component';
import { LeafWriteTagComponent } from './tag-modals/leaf-write-tag/leaf-write-tag.component';
import { ModalTagNotFoundComponent } from './tag-modals/modal-tag-not-found/modal-tag-not-found.component';



@NgModule({
  declarations: [
    MappingComponent,
    RoutineComponent,
    ProjectComponent,
    ModalProjectComponent,
    ProjectFolderComponent,
    ModalMappingComponent,
    ModalRoutineComponent,
    TagComponent,
    ModalTagComponent,
    OneTagModalComponent,
    LeafEnsSetupTagComponent,
    LeafEthernetCommunicationTagComponent,
    LeafIOLinkCommunicationTagComponent,
    LeafModbusCommunicationTagComponent,
    LeafMultipleRegisterCompareTagComponent,
    LeafMultipleWriteTagComponent,
    LeafRegisterCompareTagComponent,
    LeafStringWriteTagComponent,
    LeafTestTagComponent,
    LeafUserConfirmationTagComponent,
    LeafUserInputTagComponent,
    LeafVariableCompareTagComponent,
    LeafVariableStringWriteTagComponent,
    LeafVariableWriteTagComponent,
    LeafWriteTagComponent,
    ModalTagNotFoundComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule
  ]
})
export class SystemModule { }
