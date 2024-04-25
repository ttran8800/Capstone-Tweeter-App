import { Component, OnInit, OnDestroy } from '@angular/core';
import { IUser } from 'src/app/models/user.model';
import { Subscription, take } from 'rxjs';
import { FormBuilder, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { TweetService } from 'src/app/services/tweet.service';
import { ClockService } from 'src/app/services/clock.service';
import { ITweet } from 'src/app/models/tweet.model';

@Component({
  selector: 'app-tweet-post',
  templateUrl: './tweet-post.component.html',
  styleUrls: ['./tweet-post.component.css']
})

export class TweetPostComponent implements OnInit, OnDestroy {

  currentTime?: Date;

  user: IUser | null = null;
  private subscription: Subscription = new Subscription();

  constructor(private userService: UserService,
    private tweetService: TweetService,
    private clockService: ClockService,
    private fb: FormBuilder) { }

  ngOnInit(): void {
    this.subscription.add(
      this.userService.user$.subscribe(user => { this.user = user })
    );
    this.subscription.add(
      this.clockService.getClock().subscribe(time => { this.currentTime = time })
    )
    localStorage.removeItem('parentTweetId');
    localStorage.removeItem('tweetId');
  };

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  tweetForm = this.fb.group({
    tweetMessage: ['', [Validators.required, Validators.maxLength(50)]]
  })

  get tweetMessage() { return this.tweetForm.get('tweetMessage') }

  checkLength() {
    const maxLength = 50;
    const currentLength = this.tweetMessage?.value?.length || 0;
  }

  onSubmitHandler() {
    if (this.tweetForm.valid) {
      const message = this.tweetForm.get('tweetMessage')!.value;
      this.clockService.getClock().pipe(take(1)).subscribe(currentDate => {
        const newTweet: ITweet = {
          date: currentDate,
          message: message!,
          userId: this.user?.id!,
          parentTweetId: null
        };
        this.tweetService.createTweet(newTweet, this.user!.id!);
      });
    }
  }
}