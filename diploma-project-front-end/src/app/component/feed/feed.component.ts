import {Component, OnInit} from '@angular/core';
import {FeedService} from 'src/app/services/feed.service';
import {PostService} from 'src/app/services/post.service';
import {Page} from 'src/types/Page';
import {PostRetrieveDTO} from 'src/types/Post';
import {LikeService} from 'src/app/services/like.service';
import {AuthService} from 'src/app/services/auth/auth.service';
import {merge} from '../profile/view-profile/view-profile.component';

@Component({
  selector: 'feed-home',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {


  isCommentToggle: boolean = false;

  posts: PostRetrieveDTO[] = [];

  page = 0;
  pageSize = 10;
  totalElements: number | undefined;
  error: string = "";

  constructor(private feedService: FeedService, private postService: PostService, private likeService: LikeService, private authService: AuthService) {
  }

  ngOnInit(): void {
    this.getFeed();
  }


  getFeed() {
    this.feedService.getFeed(this.page, this.pageSize).subscribe({
      next: (response: Page<PostRetrieveDTO>) => {

        if (response?.content == undefined || response?.content == null) {
          return;
        }

        // this.posts.push(...response.content);

        if (response.content != undefined) {
          this.posts = merge(this.posts, response.content);
        }

        this.totalElements = response.totalElements;
      },
      error: (error: any) => {
        if (error.status === 400) {
          // Handle bad request error
        } else if (error.status === 401) {
          // Handle unauthorized error
        } else if (error.status === 404) {
          // Handle not found error
        } else {
          // Handle other errors
        }
        this.error = error.message;

      }
    });
  }


  onPostAdded(post: PostRetrieveDTO) {
    // adds it to the start of the array
    this.posts.unshift(post);
  }


}


function addUniqueItems(result: any[], pagination: any[]): void {
  for (const item of pagination) {
    if (!result.includes(item)) {
      result.push(item);
    }
  }
}
