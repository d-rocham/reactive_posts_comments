import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of, BehaviorSubject, catchError } from 'rxjs';
import { Post } from '../models/post';
import { newPost } from '../models/newPost';

@Injectable({
  providedIn: 'root',
})
export class WebRequestsService {
  constructor(private httpClient: HttpClient) {}

  // URL constants
  // TODO: add url for reaction, remaining crud operations
  private CREATE_POST_URL =
    'https://alpha-microservice.herokuapp.com/createPost';
  private CREATE_COMMENT_URL =
    'https://alpha-microservice.herokuapp.com/addComment';
  private GET_POST_BY_ID_URL =
    'https://beta-microservice.herokuapp.com/getPost/';
  private GET_ALL_POSTS_URL =
    'https://beta-microservice.herokuapp.com/getAllPosts';
  private LOGIN_URL = 'https://alpha-microservice.herokuapp.com/auth/login';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  // TODO: handle request errors

  public loginUser(command: any) {
    return this.httpClient.post<any>(this.LOGIN_URL, command, this.httpOptions);
  }

  public getPostById(postId: string): Observable<Post> {
    return this.httpClient.get<Post>(`${this.GET_POST_BY_ID_URL}${postId}`);
  }

  public getAllPosts(): Observable<Post[]> {
    return this.httpClient.get<Post[]>(this.GET_ALL_POSTS_URL);
  }

  public createPost(newPost: newPost, token: string): Observable<Post> {
    return this.httpClient.post<any>(this.CREATE_POST_URL, newPost, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      }),
    });
  }

  public createComment(newComment: Comment, token: string): Observable<Post> {
    return this.httpClient.post<Post>(this.CREATE_COMMENT_URL, newComment, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      }),
    });
  }
}
