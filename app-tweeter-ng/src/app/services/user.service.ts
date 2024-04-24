import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { IUser } from '../models/user.model';
import { TweetService } from './tweet.service';

@Injectable({
  providedIn: 'root'
})

export class UserService {

  private userSubject = new BehaviorSubject<IUser | null>(null);
  public user$: Observable<IUser | null> = this.userSubject.asObservable();

  constructor(private http: HttpClient,
              private tweetService: TweetService) {
    this.initializeUser();
  }

  private BASE_URL = 'http://user-service:9000/api/v1.0/tweets/user-service';

  private initializeUser(): void {
    const token = localStorage.getItem('token');
    if (token) {
      this.getUser();
    }
  }

  getUser(): void {
    this.http.get<IUser>(`${this.BASE_URL}/users/loggedInUser`).subscribe({
      next: (user) => {
        this.userSubject.next(user);
        this.tweetService.getAllUserTweet(user.id!)
      },
      error: (error) => {
        console.log('Error fetching user:', error);
        this.userSubject.next(null);
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