import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TweetReplyComponent } from './tweet-reply.component';

describe('TweetReplyComponent', () => {
  let component: TweetReplyComponent;
  let fixture: ComponentFixture<TweetReplyComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TweetReplyComponent]
    });
    fixture = TestBed.createComponent(TweetReplyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
