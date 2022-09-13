import { Post } from 'src/app/models/post';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-live-entity-container',
  templateUrl: './live-entity-container.component.html',
  styleUrls: ['./live-entity-container.component.css'],
})
export class LiveEntityContainerComponent implements OnInit {
  @Input()
  posts?: Post[];

  constructor() {}

  ngOnInit(): void {}
}
