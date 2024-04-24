import { Pipe, PipeTransform, NgZone, ChangeDetectorRef, OnDestroy } from '@angular/core';
import { interval, Subscription } from 'rxjs';

@Pipe({
  name: 'timeAgo',
  pure: false  // This is important for updating the value in the template automatically
})
export class TimeAgoPipe implements PipeTransform, OnDestroy {
  private timer!: Subscription;

  constructor(private ngZone: NgZone, private changeDetRef: ChangeDetectorRef) {}

  transform(value: Date): string {
    this.ngZone.runOutsideAngular(() => {
      if (!this.timer) {
        this.timer = interval(1000).subscribe(() => {
          this.ngZone.run(() => this.changeDetRef.markForCheck());
        });
      }
    });

    return this.timeAgo(value);
  }

  private timeAgo(value: Date): string {
    let seconds = Math.floor((new Date().getTime() - new Date(value).getTime()) / 1000);

    let interval = seconds / 31536000;
    if (interval > 1) {
      return Math.floor(interval) + " year" + (Math.floor(interval) > 1 ? "s" : "") + " ago";
    }
    interval = seconds / 2592000;
    if (interval > 1) {
      return Math.floor(interval) + " month" + (Math.floor(interval) > 1 ? "s" : "") + " ago";
    }
    interval = seconds / 86400;
    if (interval > 1) {
      return Math.floor(interval) + " day" + (Math.floor(interval) > 1 ? "s" : "") + " ago";
    }
    interval = seconds / 3600;
    if (interval > 1) {
      return Math.floor(interval) + " hour" + (Math.floor(interval) > 1 ? "s" : "") + " ago";
    }
    interval = seconds / 60;
    if (interval > 1) {
      return Math.floor(interval) + " minute" + (Math.floor(interval) > 1 ? "s" : "") + " ago";
    }
    return Math.floor(seconds) + " second" + (Math.floor(seconds) > 1 ? "s" : "") + " ago";
  }

  ngOnDestroy() {
    if (this.timer) {
      this.timer.unsubscribe();
    }
  }
}
