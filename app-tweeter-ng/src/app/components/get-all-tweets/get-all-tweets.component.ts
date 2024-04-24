import { Component, OnInit, OnDestroy } from '@angular/core';
import { AllTweetPayload } from 'src/app/payloads/all-tweet-payload.payload';
import { Subscription } from 'rxjs';
import { TweetService } from 'src/app/services/tweet.service';

@Component({
  selector: 'app-get-all-tweets',
  templateUrl: './get-all-tweets.component.html',
  styleUrls: ['./get-all-tweets.component.css']
})

export class GetAllTweetsComponent implements OnInit, OnDestroy {

  allTweetPayload: AllTweetPayload[] | null = [];

  private subscription: Subscription = new Subscription();

  constructor(private tweetService: TweetService) { }

  ngOnInit(): void {
    this.subscription.add(
      this.tweetService.allTweetWithHandle$.subscribe(payload => {
        this.allTweetPayload = payload;
        if (this.allTweetPayload) {
          this.allTweetPayload = this.allTweetPayload.sort((a, b) => {
            return new Date(b.tweet.date).getTime() - new Date(a.tweet.date).getTime()
          })
        }
      })
    )
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}