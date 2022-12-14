import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeViewComponent } from './views/home-view/home-view.component';
import { PostViewComponent } from './views/post-view/post-view.component';
import { LiveEntityContainerComponent } from './components/live-entity-container/live-entity-container.component';
import { NewEntityFormComponent } from './components/new-entity-form/new-entity-form.component';
import { NewEntityLoginPromptComponent } from './components/new-entity-login-prompt/new-entity-login-prompt.component';
import { EntityContainerComponent } from './components/entity-container/entity-container.component';
import { ReactionContainerComponent } from './components/reaction-container/reaction-container.component';
import { ReactionEntityComponent } from './components/reaction-entity/reaction-entity.component';
import { HomeHeaderComponent } from './components/home-header/home-header.component';
import { initializeApp, provideFirebaseApp } from '@angular/fire/app';
import { environment } from '../environments/environment';
import { provideAuth, getAuth } from '@angular/fire/auth';
import { LoginViewComponent } from './views/login-view/login-view.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeViewComponent,
    PostViewComponent,
    LiveEntityContainerComponent,
    NewEntityFormComponent,
    NewEntityLoginPromptComponent,
    EntityContainerComponent,
    ReactionContainerComponent,
    ReactionEntityComponent,
    HomeHeaderComponent,
    LoginViewComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    provideFirebaseApp(() => initializeApp(environment.firebase)),
    provideAuth(() => getAuth()),
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
