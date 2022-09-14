import { Component, OnInit } from '@angular/core';
import { WebSocketSubject } from 'rxjs/webSocket';
import { Post } from 'src/app/models/post';
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

  constructor(
    private socketService: WebSocketService,
    private requestService: WebRequestsService
  ) {}

  ngOnInit(): void {
    this.getPosts();
    this.connectToSocket();
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
}
