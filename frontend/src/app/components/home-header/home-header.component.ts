import { Component, Input, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-home-header',
  templateUrl: './home-header.component.html',
  styleUrls: ['./home-header.component.css'],
})
export class HomeHeaderComponent implements OnInit {
  //TODO: create state service
  constructor(private authenticationService: AuthenticationService) {}

  ngOnInit(): void {}

  async loginWithGoogle() {
    const response = await this.authenticationService.loginWithGoogle();

    // set state in service
  }
}
