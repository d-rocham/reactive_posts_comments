import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-new-entity-form',
  templateUrl: './new-entity-form.component.html',
  styleUrls: ['./new-entity-form.component.css'],
})
export class NewEntityFormComponent implements OnInit {
  @Input()
  newType!: string;

  constructor() {}

  ngOnInit(): void {}
}
