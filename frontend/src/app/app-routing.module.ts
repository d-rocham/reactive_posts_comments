import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeViewComponent } from './views/home-view/home-view.component';
import { LoginViewComponent } from './views/login-view/login-view.component';
import { PostViewComponent } from './views/post-view/post-view.component';

const routes: Routes = [
  { path: '', component: LoginViewComponent },
  { path: 'homeView', component: HomeViewComponent },
  { path: 'postView/:postId', component: PostViewComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
