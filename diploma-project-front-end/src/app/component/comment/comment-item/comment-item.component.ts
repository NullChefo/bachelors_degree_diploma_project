import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from 'src/app/services/auth/auth.service';
import {CommentRetrieveDTO} from 'src/types/Comment';
import {getTimeAgo} from '../../post/post-item/post-item.component';

@Component({
  selector: 'app-comment-item',
  templateUrl: './comment-item.component.html',
  styleUrls: ['./comment-item.component.css']
})
export class CommentItemComponent implements OnInit, OnDestroy {


  username!: string | null;
  error: string = "";
  createdFromNow: string = '';


  @Input() comment: CommentRetrieveDTO = {};

  constructor(private authService: AuthService) {
  }

  ngOnDestroy(): void {

  }

  ngOnInit(): void {
    this.username = this.authService.getUserUsername();
    this.createdFromNow = getTimeAgo(this.comment.timestamp);

  }


}

