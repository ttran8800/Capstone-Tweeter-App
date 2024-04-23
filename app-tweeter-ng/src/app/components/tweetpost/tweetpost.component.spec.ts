import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TweetpostComponent } from './tweetpost.component';

describe('TweetpostComponent', () => {
  let component: TweetpostComponent;
  let fixture: ComponentFixture<TweetpostComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TweetpostComponent]
    });
    fixture = TestBed.createComponent(TweetpostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
