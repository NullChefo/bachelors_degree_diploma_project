import {Component, Inject, Input, OnDestroy, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {CommentService} from 'src/app/services/comment.service';
import {CommentCreateDTO, CommentRetrieveDTO} from 'src/types/Comment';
import {Page} from 'src/types/Page';
import {addUniqueItems} from '../../profile/view-profile/view-profile.component';

@Component({
  selector: 'app-comment-list',
  templateUrl: './comment-list.component.html',
  styleUrls: ['./comment-list.component.css']
})
export class CommentListComponent implements OnInit, OnDestroy {

  @Input() comments: CommentRetrieveDTO[] | undefined = undefined;
  @Input() postId: number | undefined = undefined;

  // @Output() commentAdded = new EventEmitter<boolean>();

  page: number = 0;
  pageSize: number = 10;
  error: string = '';

  isCommentValid: boolean = true;

  newComment: CommentCreateDTO = {
    content: '',
    postId: undefined,
  }


  constructor(public dialogRef: MatDialogRef<CommentListComponent>, @Inject(MAT_DIALOG_DATA) public data: any, private commentService: CommentService) {
  }

  ngOnInit(): void {


    if (this.data.postId) {
      this.postId = this.data.postId;
    }

    if (this.data.comments) {
      this.comments = this.data.comments;
    }

  }


  ngOnDestroy(): void {


  }

  loadComments() {

    if (this.comments == undefined) {
      if (this.data.comments) {
        this.comments = this.data.comments;
      }
    }


    if (this.data.postId) {
      this.commentService.getCommentsForPost(this.data.postId, this.page, this.pageSize).subscribe({
        next: (response: Page<CommentRetrieveDTO>) => {

          if (response.content != undefined) {
            if (this.comments == undefined || this.comments.length == 0) {
              this.comments = response.content;
            } else {
              addUniqueItems(this.comments, response.content);
            }

          } else {
            this.comments = [];
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
  }


  createComment() {

    if (this.postId == undefined) {
      return;
    }
    this.newComment.postId = this.postId;


    if (this.newComment.content == undefined || this.newComment.content.length < 1) {
      this.isCommentValid = false;
      return;
    } else {
      this.isCommentValid = true;
    }


    this.commentService.createCommentForPostId(this.newComment).subscribe({

      next: (response: CommentRetrieveDTO) => {

        this.comments?.unshift(response); // Add the comment to the beginning of the list.
        this.newComment.content = ''; // Reset the comment.
        this.data.isCommented = true;
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
