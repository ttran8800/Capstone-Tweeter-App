import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { IUser } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  user: IUser | null = null;
  private subscription: Subscription = new Subscription();

  constructor(private router: Router, private userService: UserService) {}

    checkToken() {
      return localStorage.getItem('token') ? true: false;
    }

    onLogout() {
      this.router.navigateByUrl("/login").then(() => {
        localStorage.removeItem('token');
      });
    }

    ngOnInit(): void {
        this.subscription.add(
          this.userService.user$.subscribe(user => {
            this.user = user;
          })
        );
    }

    ngOnDestroy(): void {
      this.subscription.unsubscribe();
    }
}
