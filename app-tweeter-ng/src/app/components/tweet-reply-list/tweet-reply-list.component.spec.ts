import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TweetReplyListComponent } from './tweet-reply-list.component';

describe('TweetReplyListComponent', () => {
  let component: TweetReplyListComponent;
  let fixture: ComponentFixture<TweetReplyListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TweetReplyListComponent]
    });
    fixture = TestBed.createComponent(TweetReplyListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
