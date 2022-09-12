import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeViewComponent } from './views/home-view/home-view.component';
import { PostViewComponent } from './views/post-view/post-view.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeViewComponent,
    PostViewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
