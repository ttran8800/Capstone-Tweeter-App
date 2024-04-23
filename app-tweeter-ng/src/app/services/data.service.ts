import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { IUser } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  private userSubject = new BehaviorSubject<IUser | null>(null);
  public user$: Observable<IUser | null> = this.userSubject.asObservable();

  constructor(private http: HttpClient) { 
    if(localStorage.getItem('token')) {
      this.getUser();
      console.log(this.userSubject)
      console.log(this.user$)
    }
  }

  private BASE_URL = 'http://localhost:9000/api/v1.0/tweets/user-service';

  getUser(): void {
    this.http.get<IUser>(`${this.BASE_URL}/users/loggedInUser`).subscribe({
      next: (user) => {
        this.userSubject.next(user);
      },
      error: (error) => console.log('Error fetching user:', error)
    });
  }

  updateUser(user: IUser): void {
    this.http.put<IUser>(`${this.BASE_URL}/users/updateUser`, user).subscribe({
      next: (updatedUser) => this.userSubject.next(updatedUser),
      error: (error) => console.log('Error updating user:', error)
    });
  }
}