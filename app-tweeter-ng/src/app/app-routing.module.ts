import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { HomeComponent } from './pages/home/home.component';
import { UserProfileComponent } from "./pages/user-profile/user-profile.component";
import { CurrentUserAllTweetsComponent } from './components/current-user-all-tweets/current-user-all-tweets.component';
import { TweetReplyComponent } from './pages/tweet-reply/tweet-reply.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'current-user-all-tweet', component: CurrentUserAllTweetsComponent },
  { path: 'user-profile', component: UserProfileComponent },
  { path: 'tweet-reply-page', component: TweetReplyComponent },
  { path: '**', redirectTo: '/home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
