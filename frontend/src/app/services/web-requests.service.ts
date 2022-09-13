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
  private CREATEPOSTURL = 'http://localhost:8080/createPost';
  private CREATECOMMENTURL = 'http://localhost:8080/addComment';

  // TODO: add url for reaction, remaining crud operations

  private GETPOSTBYID = 'http://localhost:8081/getPost/';
  private GETALLPOSTS = 'http://localhost:8081/getAllPosts';

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
