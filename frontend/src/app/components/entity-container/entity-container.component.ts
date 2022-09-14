import { Component, Input, OnInit } from '@angular/core';
import { WebSocketSubject } from 'rxjs/webSocket';
import { Post } from 'src/app/models/post';

@Component({
  selector: 'app-entity-container',
  templateUrl: './entity-container.component.html',
  styleUrls: ['./entity-container.component.css'],
})
export class EntityContainerComponent implements OnInit {
  @Input()
  entity!: any;

  @Input()
  viewSocket!: WebSocketSubject<Post> | WebSocketSubject<Comment>;

  constructor() {}

  disconectSocket() {
    this.viewSocket?.complete();
  }

  isPostP(): boolean {
    return this.entity?.hasOwnProperty('aggregateId') || false;
  }

  getEntityProperty(): string {
    return this.isPostP() ? 'Title: ' : 'Content: ';
  }

  getEntityContent(): string {
    return this.isPostP() ? this.entity.title : this.entity.content;
  }

  ngOnInit(): void {}
}
