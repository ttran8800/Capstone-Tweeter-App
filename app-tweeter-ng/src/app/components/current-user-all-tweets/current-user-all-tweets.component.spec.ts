import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CurrentUserAllTweetsComponent } from './current-user-all-tweets.component';

describe('AllUserTweetsComponent', () => {
  let component: CurrentUserAllTweetsComponent;
  let fixture: ComponentFixture<CurrentUserAllTweetsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CurrentUserAllTweetsComponent]
    });
    fixture = TestBed.createComponent(CurrentUserAllTweetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
