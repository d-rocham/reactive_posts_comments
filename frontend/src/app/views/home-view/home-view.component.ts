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
    private socket: WebSocketService,
    private requestService: WebRequestsService
  ) {}

  // TODO: Add create post functionality

  ngOnInit(): void {
    this.getPosts();
    this.connectToSocket();
  }

  addPost(post: Post) {
    this.sessionPosts.unshift(post);
  }

  connectToSocket() {
    this.viewSocket = this.socket.mainViewSocket();
    this.viewSocket.subscribe((post) => {
      this.addPost(post);
    });
  }

  getPosts() {
    this.requestService.getAllPosts().subscribe((incomingPosts) => {
      this.sessionPosts = incomingPosts;
      console.log(this.sessionPosts);
    });
  }
}
