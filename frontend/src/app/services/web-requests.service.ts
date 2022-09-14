import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of, BehaviorSubject, catchError } from 'rxjs';
import { Post } from '../models/post';
import { newPost } from '../models/newPost';

@Injectable({
  providedIn: 'root',
})
export class WebRequestsService {
  // URL constants
  private CREATEPOSTURL = 'https://alpha-microservice.herokuapp.com/createPost';
  private CREATECOMMENTURL =
    'https://alpha-microservice.herokuapp.com/addComment';

  // TODO: add url for reaction, remaining crud operations

  private GETPOSTBYID = 'https://beta-microservice.herokuapp.com/getPost/';
  private GETALLPOSTS = 'https://beta-microservice.herokuapp.com/getAllPosts';

  constructor(private httpClient: HttpClient) {}

  // Resource fetching methods
  // TODO: handle request errors

  public getPostById(postId: string): Observable<Post> {
    return this.httpClient.get<Post>(`${this.GETPOSTBYID}${postId}`);
  }

  public getAllPosts(): Observable<Post[]> {
    return this.httpClient.get<Post[]>(this.GETALLPOSTS);
  }

  public createPost(newPost: newPost): Observable<Post> {
    return this.httpClient.post<Post>(this.CREATEPOSTURL, newPost, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
    });
  }

  public createComment(newComment: Comment): Observable<Post> {
    return this.httpClient.post<Post>(this.CREATECOMMENTURL, newComment, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
    });
  }
}
