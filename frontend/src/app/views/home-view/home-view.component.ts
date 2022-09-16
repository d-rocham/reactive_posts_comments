import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { WebSocketSubject } from 'rxjs/webSocket';
import { Post } from 'src/app/models/post';
import { StateService } from 'src/app/services/state.service';
import { WebRequestsService } from 'src/app/services/web-requests.service';
import { WebSocketService } from 'src/app/services/web-socket.service';

@Component({
  selector: 'app-home-view',
  templateUrl: './home-view.component.html',
  styleUrls: ['./home-view.component.css'],
})
export class HomeViewComponent implements OnInit {
  viewSocket!: WebSocketSubject<Post>;

  sessionPosts: Post[] = [];

  availableState: any;

  constructor(
    private socketService: WebSocketService,
    private requestService: WebRequestsService,
    private stateService: StateService,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (this.validateLogin()) {
      console.log('login validated');
      this.getPosts();
      this.connectToSocket();
    } else {
      console.log('not validated');
    }
  }

  addPost(post: Post) {
    this.sessionPosts.unshift(post);
  }

  /**
   * Requests a new `WebSocketSubject` from the `WebSocketService`,
   * then subscribes to it in order to listen to new incoming posts.
   */
  connectToSocket() {
    this.viewSocket = this.socketService.mainViewSocket();

    this.viewSocket.subscribe((post) => {
      this.addPost(post);
    });
  }

  /**
   * Regular HTTP GET request to fetch all posts that
   * weren't created on the current session.
   */
  getPosts() {
    this.requestService.getAllPosts().subscribe((incomingPosts) => {
      this.sessionPosts = incomingPosts;
    });
  }

  validateLogin(): boolean {
    let validationResult = false;

    this.stateService.state.subscribe((currentState) => {
      console.log(currentState);
      this.availableState = currentState;

      if (!currentState.loggedIn) {
        this.router.navigateByUrl('');
        validationResult = false;
        return;
      }
      validationResult = true;
    });
    return validationResult;
  }
}
