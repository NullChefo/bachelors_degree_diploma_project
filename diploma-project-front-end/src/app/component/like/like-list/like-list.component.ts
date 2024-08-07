import {Component, Inject, Input, OnDestroy, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {LikeService} from 'src/app/services/like.service';
import {LikeRetrieveDTO} from 'src/types/Like';
import {Page} from 'src/types/Page';
import {addUniqueItems} from '../../profile/view-profile/view-profile.component';

@Component({
  selector: 'app-like-list',
  templateUrl: './like-list.component.html',
  styleUrls: ['./like-list.component.css']
})
export class LikeListComponent implements OnInit, OnDestroy {


  @Input() likes: LikeRetrieveDTO[] = [];
  page: number = 0;
  pageSize: number = 10;
  error: string = "";


  constructor(public dialogRef: MatDialogRef<LikeListComponent>, @Inject(MAT_DIALOG_DATA) public data: any, private likeService: LikeService) {
  }

  ngOnInit(): void {
    this.loadLikes();
  }

  ngOnDestroy(): void {
  }

  loadLikes() {

    if (this.data.likes) {
      this.likes = this.data.likes;
    }


    if (this.data.postId) {

      this.likeService.getLikesForPost(this.data.postId, this.page, this.pageSize).subscribe({

        next: (response: Page<LikeRetrieveDTO>) => {

          if (response.content != undefined) {
            if (this.likes == undefined || this.likes.length == 0) {
              this.likes = response.content;
            } else {
              addUniqueItems(this.likes, response.content);
            }

          } else {
            this.likes = [];
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

    if (this.data.commentId) {
      this.likeService.getLikesForComment(this.data.commentId, this.page, this.pageSize).subscribe({
        next: (response: Page<LikeRetrieveDTO>) => {


          if (response.content != undefined) {
            if (this.likes == undefined || this.likes.length == 0) {
              this.likes = response.content;
            } else {
              addUniqueItems(this.likes, response.content);
            }

          } else {
            this.likes = [];
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


  onScroll() {
    this.loadLikes();
  }


}
