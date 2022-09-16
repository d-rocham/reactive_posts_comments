import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { StateService } from 'src/app/services/state.service';
import { WebRequestsService } from 'src/app/services/web-requests.service';

@Component({
  selector: 'app-login-view',
  templateUrl: './login-view.component.html',
  styleUrls: ['./login-view.component.css'],
})
export class LoginViewComponent implements OnInit {
  constructor(
    private authenticationService: AuthenticationService,
    private stateService: StateService,
    private router: Router,
    private requestService: WebRequestsService
  ) {}

  ngOnInit(): void {}

  async loginWithGoogle() {
    const response = await this.authenticationService.loginWithGoogle();

    if (response) {
      this.stateService.state.next({
        loggedIn: true,
        authenticatedPerson: response,
        token: '',
      });

      this.router.navigateByUrl('homeView');
      this.requestService
        .loginUser({
          username: response.user.email,
          password: response.user.email,
        })
        .subscribe({
          next: (token) => {
            if (token) {
              this.stateService.state.next({
                loggedIn: true,
                authenticatedPerson: response,
                token: token.access_token,
              });
            }
          },
        });
    }
  }
}
