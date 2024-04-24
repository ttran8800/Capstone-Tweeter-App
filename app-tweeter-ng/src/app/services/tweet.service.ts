import { Injectable, Injector } from '@angular/core';
import { BehaviorSubject, Observable, Subscription } from 'rxjs';
import { ITweet } from '../models/tweet.model';
import { HttpClient } from '@angular/common/http';
import { UserService } from './user.service';
import { IUser } from '../models/user.model';
import { OnInit, OnDestroy } from '@angular/core';
import { AllTweetPayload } from '../payloads/all-tweet-payload.payload';

@Injectable({
  providedIn: 'root'
})

export class TweetService implements OnInit, OnDestroy {

  private allUserTweetSubject = new BehaviorSubject<ITweet[] | null>(null);
  public allUserTweet$: Observable<ITweet[] | null> = this.allUserTweetSubject.asObservable();

  private allTweetWithHandleSubject = new BehaviorSubject<AllTweetPayload[] | null>(null);
  public allTweetWithHandle$: Observable<AllTweetPayload[] | null> = this.allTweetWithHandleSubject.asObservable();

  user: IUser | null = null;
  private subscription: Subscription = new Subscription();

  private _dataService: UserService | null = null;

  private BASE_URL = 'http://tweet-service:9000/api/v1.0/tweets/tweet-service'

  constructor(private http: HttpClient,
              private injector: Injector) {
                this.getAllTweetWithHandle();
               }


  private getDataService(): UserService {
    if (!this._dataService) {
      this._dataService = this.injector.get(UserService);
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
        this.getAllTweetWithHandle();
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

  getAllTweetWithHandle(): void {
    this.http.get<AllTweetPayload[]>(`${this.BASE_URL}/all`).subscribe({
      next: (payload) => {
        this.allTweetWithHandleSubject.next(payload);
      },
      error: (error) => console.log(error)
    });
  }
}