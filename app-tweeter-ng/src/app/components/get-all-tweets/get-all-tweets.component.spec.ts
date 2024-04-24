import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GetAllTweetsComponent } from './get-all-tweets.component';

describe('GetAllTweetsComponent', () => {
  let component: GetAllTweetsComponent;
  let fixture: ComponentFixture<GetAllTweetsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GetAllTweetsComponent]
    });
    fixture = TestBed.createComponent(GetAllTweetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
