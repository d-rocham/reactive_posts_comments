import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeViewComponent } from './views/home-view/home-view.component';
import { PostViewComponent } from './views/post-view/post-view.component';

const routes: Routes = [
  { path: '', redirectTo: '/homeView', pathMatch: 'full' },
  { path: 'homeView', component: HomeViewComponent },
  { path: 'postView/:postId', component: PostViewComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
