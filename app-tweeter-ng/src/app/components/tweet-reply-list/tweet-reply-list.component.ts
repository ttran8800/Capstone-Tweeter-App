import { Component, OnInit } from '@angular/core';
import { AllTweetPayload } from 'src/app/payloads/all-tweet-payload.payload';
import { TweetService } from 'src/app/services/tweet.service';


@Component({
  selector: 'app-tweet-reply-list',
  templateUrl: './tweet-reply-list.component.html',
  styleUrls: ['./tweet-reply-list.component.css']
})
export class TweetReplyListComponent implements OnInit {

  allTweetPayload: AllTweetPayload[] | null = [];


  constructor(private tweetService: TweetService) { }

  ngOnInit(): void {
    this.tweetService.getReloadTrigger().subscribe(needReload => {
      if (needReload) {
        const tweetId = Number(localStorage.getItem('tweetId'));
        if (!isNaN(tweetId)) {
          this.tweetService.getAllTweetReply(tweetId).subscribe({
            next: (payload) => {
              this.allTweetPayload = payload.sort((a, b) => new Date(b.tweet.date).getTime() - new Date(a.tweet.date).getTime());
            }
          })
        }
      }
    });

    const tweetId = Number(localStorage.getItem('tweetId'));
    if (!isNaN(tweetId)) {
      this.tweetService.getAllTweetReply(tweetId).subscribe({
        next: (payload) => {
          this.allTweetPayload = payload.sort((a, b) => new Date(b.tweet.date).getTime() - new Date(a.tweet.date).getTime());
        }
      })
    }
  }
}
