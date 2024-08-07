import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {CommentRetrieveDTO} from 'src/types/Comment';
import {faMessage as faMessageEmpty} from '@fortawesome/free-regular-svg-icons';
import {faMessage as faMessageFilled} from '@fortawesome/free-solid-svg-icons';
import {CommentListComponent} from '../comment-list/comment-list.component';
import {MatDialog} from '@angular/material/dialog';
import {AuthService} from 'src/app/services/auth/auth.service';
import {LikeService} from 'src/app/services/like.service';
import {CommentService} from 'src/app/services/comment.service';
import {Page} from 'src/types/Page';

@Component({
  selector: 'app-comment-button',
  templateUrl: './comment-button.component.html',
  styleUrls: ['./comment-button.component.css']
})
export class CommentButtonComponent implements OnInit, OnDestroy {


  faCommentEmpty = faMessageEmpty;
  faCommentFilled = faMessageFilled;
  comments: CommentRetrieveDTO[] = [];
  @Input() isCommented: boolean | undefined = false;
  @Input() commentCount: number | undefined = 0;
  @Input() postId: number | undefined = undefined;
  page: number = 0;
  size: number = 20;
  error: string = '';

  constructor(private authService: AuthService, public dialog: MatDialog, private likeService: LikeService, private commentService: CommentService) {
  }

  ngOnInit() {

    if (this.isCommented == undefined) {
      this.isCommented = false;
    }

    if (this.commentCount == undefined) {
      this.commentCount = 0;
    }


  }

  ngOnDestroy() {
  }

  loadComments(): void {

    if (this.postId != undefined) {


      this.commentService.getCommentsForPost(this.postId, this.page, this.size).subscribe({

        next: (response: Page<CommentRetrieveDTO>) => {

          if (response.content == undefined) {
            this.comments = [];
            return;
          }
          if (this.comments == undefined || this.comments.length == 0) {
            this.comments = response.content;
          } else {
            this.comments = [...this.comments, ...response.content];
          }

          if (response.totalElements != undefined) {
            this.commentCount = response.totalElements;
          }

          this.page = this.page + 1;
          console.log(this.comments);
          this.openComments('0ms', '0ms');
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


  openComments(enterAnimationDuration: string, exitAnimationDuration: string): void {
    this.dialog.open(CommentListComponent, {
      width: '60%',
      enterAnimationDuration,
      exitAnimationDuration,
      data: {
        comments: this.comments,
        postId: this.postId,
        isCommented: this.isCommented,
      },

    }).afterClosed().subscribe(res => {

      this.comments = [];
    });
  }


}
