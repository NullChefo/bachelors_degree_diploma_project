import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {AuthService} from 'src/app/services/auth/auth.service';
import {FeedService} from 'src/app/services/feed.service';
import {LikeService} from 'src/app/services/like.service';
import {PostService} from 'src/app/services/post.service';
import {PostRetrieveDTO} from 'src/types/Post';
import {MatDialog} from '@angular/material/dialog';
import {faCheck, faPencil, faTrash} from '@fortawesome/free-solid-svg-icons';
import {MarkdownService} from 'ngx-markdown';

@Component({
  selector: 'app-post-item',
  templateUrl: './post-item.component.html',
  styleUrls: ['./post-item.component.css']
})
export class PostItemComponent implements OnInit, OnDestroy {


  faEdit = faPencil;
  faDelete = faTrash;
  faBadgeCheck = faCheck; // todo: change icon faBadgeCheck


  @Input() post: PostRetrieveDTO = {};

  @Output() deleteClicked = new EventEmitter<number>();


  username!: string | null;
  error: string = "";

  commentContent: string = "";
  isCommentValid: boolean = true;

  createdFromNow: string = "";

  markdownContent = '';


  constructor(private feedService: FeedService, private postService: PostService, private likeService: LikeService, private authService: AuthService, public dialog: MatDialog, private mdService: MarkdownService) {

  }

  ngOnInit(): void {
    if (this.post.content) {
      this.markdownContent = this.post.content;
    }


    // this.createdFromNow = calculateTimeDuration(this.post.createdAt);
    this.createdFromNow = getTimeAgo(this.post.createdAt);
    this.username = this.authService.getUserUsername();
  }


  onLoadMarkdown(data: any) {
    console.log(data);
  }

  onErrorMarkdown(data: any) {
    console.log(data);
  }

  editPost() {
    throw new Error('Method not implemented.');
  }


  onDelete(): void {
    this.deleteClicked.emit(this.post?.id);
  }


  ngOnDestroy(): void {

  }

}

// export function calculateTimeDuration(date: Date | undefined): string {
//     if(date == undefined){
//         return "";
//         console.log("date is undefined");
//     }

//     var now_temp = new Date();
//     var now_utc = Date.UTC(now_temp.getUTCFullYear(), now_temp.getUTCMonth(),
//     now_temp.getUTCDate(), now_temp.getUTCHours(),
//                 now_temp.getUTCMinutes(), now_temp.getUTCSeconds());

//     let now_data = new Date(now_utc);

//     let targetDate = new Date(date); // Convert the input date string to a Date object

//     let duration = now_data.getTime() - targetDate.getTime();
//     let seconds = Math.floor(duration / 1000) % 60;
//     let minutes = Math.floor(duration / 1000 / 60) % 60;
//     let hours = Math.floor(duration / 1000 / 3600) % 24;
//     let days = Math.floor(duration / 1000 / 3600 / 24);

//     let durationString = "";
//     if (days > 0) {
//       durationString += `${days} day${days > 1 ? "s" : ""} `;
//     }
//     if (hours > 0) {
//       durationString += `${hours} hour${hours > 1 ? "s" : ""} `;
//     }
//     if (minutes > 0) {
//       durationString += `${minutes} minute${minutes > 1 ? "s" : ""} `;
//     }
//     if (seconds > 0) {
//       durationString += `${seconds} second${seconds > 1 ? "s" : ""}`;
//     }
//     if (durationString == "") {
//         durationString = "less than a second";
//     }

//   return durationString;
// }

export function getTimeAgo(timestamp: Date | undefined) {

  if (timestamp == undefined) {
    return "";
    console.log("date is undefined");
  }

  let now_temp = new Date();
  let now_utc = Date.UTC(now_temp.getUTCFullYear(), now_temp.getUTCMonth(),
    now_temp.getUTCDate(), now_temp.getUTCHours(),
    now_temp.getUTCMinutes(), now_temp.getUTCSeconds());


  let now: Date = new Date(now_utc);
  let convertedNow: string = now.toISOString();
  console.log(convertedNow);

  let newNow: Date = new Date(convertedNow);
  console.log(convertedNow);

    let convertedTarget: string = timestamp.toString();
    let target: Date = new Date(convertedTarget);
    console.log("convertedTarget: " + convertedTarget)

  

  let diff = newNow.getTime() - target.getTime() ;
  
  console.log(diff);




  // Calculate time differences in seconds, minutes, hours, and days
  const seconds = Math.floor(diff / 1000);
  const minutes = Math.floor(seconds / 60);
  const hours = Math.floor(minutes / 60);
  const days = Math.floor(hours / 24);

  // Determine the appropriate time unit to display
  if (days > 0) {
    return days + "d";
  } else if (hours > 0) {
    return hours + "h";
  } else if (minutes > 0) {
    return minutes + "m";
  } else {
    return seconds + "s";
  }
}
