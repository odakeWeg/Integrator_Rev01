import { TestInitializerComponent } from './test/test-initializer/test-initializer.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TestInitializerV2Component } from './test/test-initializer-v2/test-initializer-v2.component';
import { TestInitializerV3Component } from './test/test-initializer-v3/test-initializer-v3.component';

const routes: Routes = [
  {
    path: '', 
    redirectTo: '/test', 
    pathMatch:'full'
  },
  {
    path: 'test',
    component: TestInitializerV3Component
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
