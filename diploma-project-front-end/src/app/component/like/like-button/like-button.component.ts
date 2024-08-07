import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {faHeart as faHeartEmpty} from '@fortawesome/free-regular-svg-icons';
import {faHeart as faHearthFilled} from '@fortawesome/free-solid-svg-icons';
import {AuthService} from 'src/app/services/auth/auth.service';
import {LikeService} from 'src/app/services/like.service';
import {LikeRetrieveDTO} from 'src/types/Like';
import {LikeListComponent} from '../like-list/like-list.component';
import {Page} from 'src/types/Page';

@Component({
  selector: 'app-like-button',
  templateUrl: './like-button.component.html',
  styleUrls: ['./like-button.component.css']
})
export class LikeButtonComponent implements OnInit, OnDestroy {
  faHeartFilled = faHearthFilled;
  faHeartEmpty = faHeartEmpty;


  likes: LikeRetrieveDTO[] = [];
  username!: string | null;
  error: string = "";

  @Input() isLiked: boolean | undefined = undefined;
  @Input() likeCount: number | undefined = undefined;

  @Input() postId: number | undefined = undefined;
  @Input() commentId: number | undefined = undefined;


  page: number = 0;
  size: number = 20;


  constructor(private authService: AuthService, public dialog: MatDialog, private likeService: LikeService) {
  }

  ngOnDestroy(): void {

  }

  ngOnInit(): void {
    if (this.isLiked == undefined) {
      this.isLiked = false;
    }
    if (this.likeCount == undefined) {
      this.likeCount = 0;

    }

  }


  openLikes(enterAnimationDuration: string, exitAnimationDuration: string): void {
    this.loadLikes();

    this.dialog.open(LikeListComponent, {
      width: '60%',
      enterAnimationDuration,
      exitAnimationDuration,
      data: {
        likes: this.likes
      }
    });

  }

// TODO refactor the api
  loadLikes(): void {
    if (this.postId != undefined) {
      this.likeService.getLikesForPost(this.postId, this.page, this.size).subscribe({

        next: (response: Page<LikeRetrieveDTO>) => {

          if (response.content == undefined) {
            this.likes = [];
            return;
          }
          if (this.likes == undefined || this.likes.length == 0) {
            this.likes = response.content;
          } else {
            this.likes = [...this.likes, ...response.content];
          }

          if (response.totalElements != undefined) {
            this.likeCount = response.totalElements;
          }

          this.page = this.page + 1;
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
    if (this.commentId != undefined) {
      this.likeService.getLikesForComment(this.commentId, this.page, this.size).subscribe({

        next: (response: Page<LikeRetrieveDTO>) => {

          if (response.content == undefined) {
            this.likes = [];
            return;
          }
          if (this.likes == undefined || this.likes.length == 0) {
            this.likes = response.content;
          } else {
            this.likes = [...this.likes, ...response.content];
          }

          if (response.totalElements != undefined) {
            this.likeCount = response.totalElements;
          }

          this.page = this.page + 1;
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


  // TODO refactor the api
  like() {


    if (this.postId != undefined) {
      this.likeService.likeDislikePostById(this.postId).subscribe({

        next: (response: LikeRetrieveDTO) => {
          if (this.likeCount == undefined) {
            this.likeCount = 0;
          }
          if (this.likes?.find(like => like.id == response.id) != undefined) {
            this.likes = this.likes?.filter(like => like.id != response.id);
            this.likeCount = this.likeCount - 1;
            this.isLiked = false;
          } else {

            // if(this.isLiked== true){
            //   this.likeCount = this.likeCount -1;
            //   this.isLiked = false;
            // }else{
            //   this.likeCount = this.likeCount + 1;
            //   this.isLiked = true;
            // }
            this.isLiked = true;
            this.likes?.push(response);
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

    if (this.commentId != undefined) {

      this.likeService.likeDislikeComment(this.commentId).subscribe({

        next: (response: LikeRetrieveDTO) => {
          if (this.likeCount == undefined) {
            this.likeCount = 0;
          }
          if (this.likes?.find(like => like.id == response.id) != undefined) {
            this.likes = this.likes?.filter(like => like.id != response.id);
            this.likeCount = this.likeCount - 1;
            this.isLiked = false;
          } else {
            this.likeCount = this.likeCount + 1;
            this.isLiked = true;
            this.likes?.push(response);
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

  }


}


