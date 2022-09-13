import { Component, Input, OnInit } from '@angular/core';
import { Post } from 'src/app/models/post';

@Component({
  selector: 'app-entity-container',
  templateUrl: './entity-container.component.html',
  styleUrls: ['./entity-container.component.css'],
})
export class EntityContainerComponent implements OnInit {
  @Input()
  post?: Post;

  constructor() {}

  ngOnInit(): void {}
}
