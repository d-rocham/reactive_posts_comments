import { Post } from 'src/app/models/post';
import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { WebSocketSubject } from 'rxjs/webSocket';

@Component({
  selector: 'app-live-entity-container',
  templateUrl: './live-entity-container.component.html',
  styleUrls: ['./live-entity-container.component.css'],
})
export class LiveEntityContainerComponent implements OnInit {
  @Input()
  collection!: any;

  @Input()
  viewSocket!: any;

  @Input()
  collectionType!: string;

  constructor() {}

  /**
   * Predicate. Checks whether the collection is emtpy.
   * @returns boolean
   */
  isCollectionEmptyP(): boolean {
    return this.collection?.length > 0;
  }

  ngOnInit(): void {}
}
