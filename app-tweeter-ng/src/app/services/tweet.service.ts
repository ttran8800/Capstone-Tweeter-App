import { Injectable, Injector } from '@angular/core';
import { BehaviorSubject, Observable, Subscription } from 'rxjs';
import { ITweet } from '../models/tweet.model';
import { HttpClient } from '@angular/common/http';
import { DataService } from './data.service';
import { IUser } from '../models/user.model';
import { OnInit, OnDestroy } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TweetService implements OnInit{

  private allUserTweetSubject = new BehaviorSubject<ITweet[] | null>(null);
  public allUserTweet$: Observable<ITweet[] | null> = this.allUserTweetSubject.asObservable();

  user: IUser | null = null;
  private subscription: Subscription = new Subscription();

  private _dataService: DataService | null = null;

  private BASE_URL = 'http://localhost:9000/api/v1.0/tweets/tweet-service'

  constructor(private http: HttpClient,
              private injector: Injector) { }

  private getDataService(): DataService {
    if (!this._dataService) {
      this._dataService = this.injector.get(DataService);
    }
    return this._dataService;
  }
  
  ngOnInit(): void {
      this.subscription.add(
        this.getDataService().user$.subscribe(user => {
          this.user = user;
        })
      )
  }
  
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  createTweet(tweet: ITweet, userId: number): void {
    this.http.post<ITweet>(`${this.BASE_URL}/${userId}/addTweet`, tweet).subscribe({
      next: () => {
        this.subscription.add(this.getDataService().user$.subscribe(user => {this.user = user}))
        this.getAllUserTweet(this.user!.id!);
      },
      error: (error) => {
        console.log(error)
      }
    })
  }

  getAllUserTweet(userId: number): void {
    this.http.get<ITweet[]>(`${this.BASE_URL}/${userId}/all`).subscribe({
      next: (tweet) => {
        this.allUserTweetSubject.next(tweet)
      },
      error: (error) => console.log(error)
    });
  }
}