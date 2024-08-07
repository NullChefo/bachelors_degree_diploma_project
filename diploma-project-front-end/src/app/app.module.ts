import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './pages/informational/home/home.component';
import {AboutComponent} from './pages/informational/about/about.component';
import {TopPanelComponent} from './component/panels/top-panel/top-panel.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AuthComponent} from './component/auth/auth.component';
import {FeedComponent} from './component/feed/feed.component';
import {RegisterComponent} from './pages/auth/register/register.component';
import {PasswordResetComponent} from './pages/auth/password-reset/password-reset.component';
import {SetNewPasswordComponent} from './pages/auth/set-new-password/set-new-password.component';
import {ThanksForRegistrationComponent} from './pages/auth/thanks-for-registration/thanks-for-registration.component';
import {ValidateRegistrationComponent} from './pages/auth/validate-registration/validate-registration.component';
import {PageNotFoundComponent} from './pages/informational/page-not-found/page-not-found.component';
import {LoggedOutComponent} from './pages/auth/logged-out/logged-out.component';
import {PrivacyPolicyComponent} from './pages/informational/privacy-policy/privacy-policy.component';
import {TermsOfServiceComponent} from './pages/informational/terms-of-service/terms-of-service.component';
import {ConnectionsComponent} from './pages/connections/connections.component';
import {AddPostComponent} from './component/post/add-post/add-post.component';
import {ListPostComponent} from './component/post/list-post/list-post.component';
import {DisclaimersComponent} from './pages/informational/disclaimers/disclaimers.component';
import {BrowserAnimationsModule, NoopAnimationsModule} from '@angular/platform-browser/animations';
import {PostItemComponent} from './component/post/post-item/post-item.component';
import {CommentListComponent} from './component/comment/comment-list/comment-list.component';
import {CommentItemComponent} from './component/comment/comment-item/comment-item.component';
import {LikeListComponent} from './component/like/like-list/like-list.component';
import {LikeItemComponent} from './component/like/like-item/like-item.component';
import {ChatListComponent} from './component/chat/chat-list/chat-list.component';
import {ChatItemComponent} from './component/chat/chat-item/chat-item.component';
import {CustomInterceptor} from './utils/CustomInterceptor';
import {MediaItemComponent} from './component/media/media-item/media-item.component';
import {MediaListComponent} from './component/media/media-list/media-list.component';
import {EditProfileComponent} from './component/profile/edit-profile/edit-profile.component';
import {ViewProfileComponent} from './component/profile/view-profile/view-profile.component';
import {GroupListComponent} from './component/group/group-list/group-list.component';
import {GroupItemComponent} from './component/group/group-item/group-item.component';
import {GroupPageComponent} from './component/group/group-page/group-page.component';
import {ChatPageComponent} from './component/chat/chat-page/chat-page.component';
import {ConnectionPageComponent} from './component/connection/connection-page/connection-page.component';
import {ConnectionListComponent} from './component/connection/connection-list/connection-list.component';
import {ConnectionItemComponent} from './component/connection/connection-item/connection-item.component';
import {UserItemComponent} from './component/user/user-item/user-item.component';
import {UserListComponent} from './component/user/user-list/user-list.component';
import {InfiniteScrollModule} from 'ngx-infinite-scroll';
import {DeveloperModeComponent} from './pages/informational/developer-mode/developer-mode.component';
import {MatNativeDateModule} from '@angular/material/core';
import {MatDialogModule} from '@angular/material/dialog';
import {LikeButtonComponent} from './component/like/like-button/like-button.component';
import {CommentButtonComponent} from './component/comment/comment-button/comment-button.component';
import {MarkdownModule} from 'ngx-markdown';
import {MediaAddComponent} from './component/media/media-add/media-add.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AboutComponent,
    TopPanelComponent,
    AuthComponent,
    FeedComponent,
    RegisterComponent,
    PasswordResetComponent,
    SetNewPasswordComponent,
    ThanksForRegistrationComponent,
    ValidateRegistrationComponent,
    PageNotFoundComponent,
    LoggedOutComponent,
    PrivacyPolicyComponent,
    TermsOfServiceComponent,
    ConnectionsComponent,
    AddPostComponent,
    ListPostComponent,
    DisclaimersComponent,
    PostItemComponent,
    CommentListComponent,
    CommentItemComponent,
    LikeListComponent,
    LikeItemComponent,
    ChatListComponent,
    ChatItemComponent,
    MediaItemComponent,
    MediaListComponent,
    EditProfileComponent,
    ViewProfileComponent,
    GroupListComponent,
    GroupItemComponent,
    GroupPageComponent,
    ChatPageComponent,
    ConnectionPageComponent,
    ConnectionListComponent,
    ConnectionItemComponent,
    UserItemComponent,
    UserListComponent,
    DeveloperModeComponent,
    LikeButtonComponent,
    CommentButtonComponent,
    MediaAddComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FontAwesomeModule,
    FormsModule,
    HttpClientModule,
    NoopAnimationsModule,
    InfiniteScrollModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatNativeDateModule,
    MatDialogModule,
    ReactiveFormsModule,
    MarkdownModule.forRoot()
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: CustomInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
