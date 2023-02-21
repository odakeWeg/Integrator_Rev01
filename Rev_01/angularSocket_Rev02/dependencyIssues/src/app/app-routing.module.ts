import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TestInitializerComponent } from './automatic-test/test-initializer/test-initializer.component';

const routes: Routes = [
  {
    path: '', 
    redirectTo: '/test', 
    pathMatch:'full'
  },
  {
    path: 'test',
    component: TestInitializerComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
