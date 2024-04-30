import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Subscription, take } from 'rxjs';
import { ITweet } from 'src/app/models/tweet.model';
import { ClockService } from 'src/app/services/clock.service';
import { TweetService } from 'src/app/services/tweet.service';
import { IUser } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-tweet-reply',
  templateUrl: './tweet-reply.component.html',
  styleUrls: ['./tweet-reply.component.css']
})
export class TweetReplyComponent implements OnInit, OnDestroy {

  tweet?: ITweet;
  userHandle?: string;

  user: IUser | null = null;
  private subscription: Subscription = new Subscription();

  isReplyMessageBox: boolean = false;
  currentTime?: Date;

  message = ''
  alertClass = ''
  countdown: number = 2;

  constructor(private fb: FormBuilder,
    private clockService: ClockService,
    private tweetService: TweetService,
    private userService: UserService,
    private router: Router) { }

  ngOnInit(): void {
    const tweetId = Number(localStorage.getItem('tweetId'));
    if (!isNaN(tweetId)) {
      this.tweetService.getTweetByTweetId(tweetId).subscribe({
        next: (payload) => {
          this.tweet = payload?.tweet;
          this.userHandle = payload?.loginId;
        },
        error: (error) => {
          console.log('Error fetching tweet:', error);
        }
      });
    } else {
      console.log('Invalid tweetId:', tweetId);
    }
    this.subscription.add(
      this.userService.user$.subscribe(user => { this.user = user })
    );
    this.subscription.add(
      this.clockService.getClock().subscribe(time => { this.currentTime = time })
    )
    const token = (localStorage.getItem('token'));
    if (!token) {
      this.router.navigateByUrl('/home');
    }
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  tweetReplyForm = this.fb.group({
    replyMessage: ['', Validators.required]
  })

  get replyMessage() { return this.tweetReplyForm.get('replyMessage') }

  onReplyBtnClick() {
    this.isReplyMessageBox = true;
  }

  onCancelBtnClick() {
    this.isReplyMessageBox = false;
    this.tweetReplyForm.reset();
  }

  checkLength() {
    const maxLength = 50;
    const currentLength = this.replyMessage?.value?.length || 0;
  }

  onSubmitBtnClick() {
    if (this.tweetReplyForm.valid) {
      const message = this.tweetReplyForm.get('replyMessage')!.value;
      const parentTweetId = Number(localStorage.getItem('parentTweetId'));
      const tweetId = Number(localStorage.getItem('tweetId'));
      this.clockService.getClock().pipe(take(1)).subscribe(
        currentDate => {
          const newTweet: ITweet = {
            tweetId: tweetId,
            date: currentDate,
            message: message!,
            userId: this.user?.id!,
            parentTweetId: parentTweetId
          };
          this.tweetService.createTweet(newTweet, this.user!.id!);
          this.message = 'Rely Posted';
          this.alertClass = 'alert alert-success'
          const interval = setInterval(() => {
            this.countdown--;
            if (this.countdown === 0) {
              clearInterval(interval);
              this.message = '';
              this.alertClass = ''
              this.onCancelBtnClick();
              this.tweetService.triggerReload();
            }
          }, 1000)
        });
    }
  }
}