import { Component, OnInit } from '@angular/core';
import { POSTS } from 'src/app/mock_data/mock-posts';

@Component({
  selector: 'app-home-view',
  templateUrl: './home-view.component.html',
  styleUrls: ['./home-view.component.css'],
})
export class HomeViewComponent implements OnInit {
  posts = POSTS;

  constructor() {}

  ngOnInit(): void {}
}
