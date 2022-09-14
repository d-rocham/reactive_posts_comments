import { WebSocketSubject } from 'rxjs/webSocket';
import { Component, Input, OnInit } from '@angular/core';
import { Post } from 'src/app/models/post';
import { Comment } from 'src/app/models/comment';
import { ActivatedRoute } from '@angular/router';
import { WebSocketService } from 'src/app/services/web-socket.service';
import { WebRequestsService } from 'src/app/services/web-requests.service';

@Component({
  selector: 'app-post-view',
  templateUrl: './post-view.component.html',
  styleUrls: ['./post-view.component.css'],
})
export class PostViewComponent implements OnInit {
  post!: Post;
  viewSocket?: WebSocketSubject<Comment>;

  constructor(
    private route: ActivatedRoute,
    private requestService: WebRequestsService,
    private socketService: WebSocketService
  ) {}

  ngOnInit(): void {
    this.getPost();
  }

  getPost() {
    const targetId: string | null = this.route.snapshot.paramMap.get('postId');

    if (targetId) {
      this.requestService.getPostById(targetId).subscribe((foundPost) => {
        this.post = foundPost;
        this.connectToOwnSocket(
          this.post ? this.post.aggregateId : 'mainSpace'
        );
      });
    }
  }

  connectToOwnSocket(path: string) {
    this.viewSocket = this.socketService.postViewSocket(path);
    this.viewSocket.subscribe((incomingComment) =>
      this.addComment(incomingComment)
    );
  }

  addComment(newComment: Comment) {
    this.post?.comments.unshift(newComment);
  }

  // TODO: create comment functionality
}
