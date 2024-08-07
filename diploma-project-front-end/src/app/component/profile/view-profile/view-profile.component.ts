import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {faLinkedin} from '@fortawesome/free-brands-svg-icons';
import {faBriefcase, faCheck, faGraduationCap, faLink, faSchool, faUsers} from '@fortawesome/free-solid-svg-icons';
import {Subscription} from 'rxjs';
import {AuthService} from 'src/app/services/auth/auth.service';
import {PostService} from 'src/app/services/post.service';
import {ProfileService} from 'src/app/services/profile.service';
import {Page} from 'src/types/Page';
import {PostRetrieveDTO} from 'src/types/Post';
import {ProfileRetrieveDTO} from 'src/types/User';

@Component({
  selector: 'app-view-profile',
  templateUrl: './view-profile.component.html',
  styleUrls: ['./view-profile.component.css']
})
export class ViewProfileComponent implements OnInit {

  faWork = faBriefcase;

  faGraduationCap = faGraduationCap;
  faSchool = faSchool;
  faLink = faLink;
  faLinkedIn = faLinkedin;
  faUsers = faUsers
  faBadgeCheck = faCheck; // todo: change icon faBadgeCheck


  profile: ProfileRetrieveDTO | undefined;
  // currentUser: UserRetrieveDTO = {};
  error: string = "";
  username: string = "";
  sub: Subscription = new Subscription;
  posts: PostRetrieveDTO[] = [];
  currentUsername: string | null = null;

  page: number = 0;
  size: number = 5;
  isLoading = false;

  constructor(private profileService: ProfileService, private activationRoute: ActivatedRoute, private postService: PostService, private authService: AuthService) {
  }

  toggleLoading = () => this.isLoading = !this.isLoading;

  ngOnInit(): void {

    this.currentUsername = this.authService.getUserUsername();

    this.sub = this.activationRoute.paramMap.subscribe(params => {
      var username = params.get('username');
      if (username) {
        this.username = username.toString();
      }
    })

    this.profileService.getProfileByUsername(this.username).subscribe({

      next: (response: ProfileRetrieveDTO) => {
        this.profile = response;

        if (this.profile.username != undefined) {
          this.loadPosts();
        }

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
        this.error = error.error.message;
      }

    })


  }

  loadPosts() {
    if (this.profile != undefined && this.profile.username != undefined) {

      this.postService.getPostsByForUsername(this.profile.username, this.page, this.size).subscribe({

        next: (response: Page<PostRetrieveDTO>) => {

          if (response.content != undefined) {
            if (this.posts == undefined || this.posts.length == 0) {
              this.posts = response.content;
            } else {
              addUniqueItems(this.posts, response.content);
            }

          } else {
            this.posts = [];
          }


          this.page++;
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
          this.error = error.error.message;
        },
        // complete:()=>{}

      })
    }
  }

  onScroll = () => {
    // console.log("scrolled");
    this.loadPosts();
  }


  deleteProfile(username: string | undefined) {

    if (username != undefined) {

      this.profileService.deleteProfileByUsername(username).subscribe({
        next: () => {
          this.authService.login();
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
          this.error = error.error.message;
        }
      })
    }
  }

}


export function merge(array1: any[], array2: any[]): any[] {
  return [...new Set([...array1, ...array2])];
}

export function addUniqueItems(result: any[], pagination: any[]): void {
  for (const item of pagination) {
    if (!result.includes(item)) {
      result.push(item);
    }
  }
}
