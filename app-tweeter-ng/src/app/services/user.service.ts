import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { IUser } from '../models/user.model';
import { TweetService } from './tweet.service';
import { Router } from '@angular/router';
@Injectable({
  providedIn: 'root'
})

export class UserService {

  private userSubject = new BehaviorSubject<IUser | null>(null);
  public user$: Observable<IUser | null> = this.userSubject.asObservable();

  constructor(private http: HttpClient,
              private tweetService: TweetService,
              private router: Router) {
    if (localStorage.getItem('token')) {
      this.getUser();
    }
  }


  // private BASE_URL = 'http://52.14.237.71:9000/api/v1.0/tweets/user-service';
  private BASE_URL = 'http://localhost:9000/api/v1.0/tweets/user-service';
  // private BASE_URL = 'http://api-gateway:9000/api/v1.0/tweets/user-service';

  getUser(): void {
    this.http.get<IUser>(`${this.BASE_URL}/users/loggedInUser`).subscribe({
      next: (user) => {
        this.userSubject.next(user);
        this.tweetService.getAllUserTweet(user.id!)
      },
      error: (error) => {
        console.log('Error fetching user:', error);
        this.userSubject.next(null);
        localStorage.removeItem('token');
        this.router.navigateByUrl('/home');
      }
    });
  }

  updateUser(user: IUser): void {
    this.http.put<IUser>(`${this.BASE_URL}/users/updateUser`, user).subscribe({
      next: (updatedUser) => this.userSubject.next(updatedUser),
      error: (error) => console.log('Error updating user:', error)
    });
  }
}