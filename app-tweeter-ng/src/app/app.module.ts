import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http'
import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { UserProfileComponent } from './pages/user-profile/user-profile.component';
import { HttpTokenLoader } from './utils/HttpTokenLoader.util';
import { TweetPostComponent } from './components/tweet-post/tweet-post.component';
import { CurrentUserAllTweetsComponent } from './components/current-user-all-tweets/current-user-all-tweets.component';
import { TimeAgoPipe } from './pipes/timeAgo.pipe';
import { GetAllTweetsComponent } from './components/get-all-tweets/get-all-tweets.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    NavbarComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    UserProfileComponent,
    TweetPostComponent,
    TimeAgoPipe,
    CurrentUserAllTweetsComponent,
    GetAllTweetsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: HttpTokenLoader, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
