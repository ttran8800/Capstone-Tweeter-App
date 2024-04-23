import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { IUser } from 'src/app/models/user.model';
import { DataService } from 'src/app/services/data.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  user: IUser | null = null;
  private subscription: Subscription = new Subscription;


  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.subscription.add(
      this.dataService.user$.subscribe(user => { this.user = user; })
    );
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  checkToken() {
    return localStorage.getItem('token') ? true:false
  }
}
