import { Component, Input, OnInit } from '@angular/core';
import { WebRequestsService } from 'src/app/services/web-requests.service';
import { newPost } from 'src/app/models/newPost';
import { Comment } from 'src/app/models/comment';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-new-entity-form',
  templateUrl: './new-entity-form.component.html',
  styleUrls: ['./new-entity-form.component.css'],
})
export class NewEntityFormComponent implements OnInit {
  @Input()
  newType!: string;

  @Input()
  token!: string;

  createNew(): any {}

  constructor(
    private requestService: WebRequestsService,
    private route: ActivatedRoute
  ) {}

  generateId(): string {
    return (Math.random() * (10000000 - 100000) + 100000).toString();
  }

  createNewEntity(): void {
    const newEntity: any =
      this.newType === 'post'
        ? {
            postID: this.generateId(),
            // TODO: Bad, temporary implementation. Change access to form data below
            title: (
              document.querySelector('#entityFormContent') as HTMLInputElement
            ).value,
            author: (
              document.querySelector('#entityFormAuthor') as HTMLInputElement
            ).value,
          }
        : {
            commentID: this.generateId(),
            postID: this.route.snapshot.paramMap.get('postId'),
            author: (
              document.querySelector('#entityFormAuthor') as HTMLInputElement
            ).value,
            content: (
              document.querySelector('#entityFormContent') as HTMLInputElement
            ).value,
          };

    console.log(this.token);

    this.newType === 'post'
      ? this.requestService.createPost(newEntity, this.token).subscribe()
      : this.requestService.createComment(newEntity, this.token).subscribe();
  }

  ngOnInit(): void {}
}
