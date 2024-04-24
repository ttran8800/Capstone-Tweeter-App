import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { ITweet } from 'src/app/models/tweet.model';
import { IUser } from 'src/app/models/user.model';
import { DataService } from 'src/app/services/data.service';
import { TweetService } from 'src/app/services/tweet.service';

@Component({
  selector: 'app-current-user-all-tweets',
  templateUrl: './current-user-all-tweets.component.html',
  styleUrls: ['./current-user-all-tweets.component.css']
})
export class CurrentUserAllTweetsComponent implements OnInit, OnDestroy {

  public allUserTweets: ITweet[] | null = null;

  private subscription: Subscription = new Subscription();



  user: IUser | null = null;

  constructor(private dataService: DataService,
    private tweetService: TweetService) { }



  ngOnInit(): void {
    this.subscription.add(
      this.dataService.user$.subscribe(user => this.user = user)
    );
    this.subscription.add(
      this.tweetService.allUserTweet$.subscribe(allTweets => {
        this.allUserTweets = allTweets; 
        if (this.user && this.allUserTweets) {
          this.allUserTweets = this.allUserTweets?.sort((a, b) => new Date(b.date).getTime() - new Date(a.date).getTime())
        }
      })
    );
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
