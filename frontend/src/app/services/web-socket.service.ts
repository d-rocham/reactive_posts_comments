import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { Injectable } from '@angular/core';
import { Post } from '../models/post';
import { Comment } from '../models/comment';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  constructor() {}

  mainViewSocket(): WebSocketSubject<Post> {
    return webSocket(
      'wss://gamma-microservice.herokuapp.com/retrieve/mainSpace'
    );
  }

  /**
   * @param targetPostId A `Post` `aggregateId` property.
   * @returns `WebSocketSubject` connection to interact with the requested post session.
   */
  postViewSocket(targetPostId: string): WebSocketSubject<Comment> {
    return webSocket(
      `wss://gamma-microservice.herokuapp.com/retrieve/${targetPostId}`
    );
  }
}
