import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from 'src/app/services/auth/auth.service';
import {PostService} from 'src/app/services/post.service';
import {PostRetrieveDTO} from 'src/types/Post';
import {UserRetrieveDTO} from 'src/types/User';

@Component({
  selector: 'app-list-post',
  templateUrl: './list-post.component.html',
  styleUrls: ['./list-post.component.css']
})
export class ListPostComponent implements OnInit, OnDestroy {


  @Input() posts: PostRetrieveDTO[] = [];

  @Input() currentUser: UserRetrieveDTO = {};

  error: string = "";


  constructor(private postService: PostService, private authService: AuthService) {
  }

  ngOnInit(): void {
    // this.authService.getCurrentUserRestCall().subscribe({
    //     next: (response: UserRetrieveDTO) => {
    //         this.currentUser = response;
    //     },
    //     error: (error: HttpErrorResponse) => {
    //         console.log("Error: " + error);
    //         return;
    //     }
    // })


    // if (this.groupId == null) {
    //     return;
    // }

    // this.postService.getAllPostsForGroupPageableNew(this.groupId, this.page, this.pageSize).subscribe({
    //     next: (response: any) => {
    //         this.posts = response.content;
    //         // Do something with the posts array
    //     },
    //     error: (error: any) => {
    //         // TODO handle them
    //         if (error.status === 400) {
    //             this.error = error.error.message;
    //         } else if (error.status === 401) {
    //             this.error = error.error.message;
    //         } else if (error.status === 404) {
    //             this.error = error.error.message;
    //         } else {
    //             this.error = error.error.message;
    //         }
    //     }
    // });

  }


  ngOnDestroy(): void {

  }


  onDelete(id: number): void {
    this.postService.deletePost(id).subscribe({
      next: () => {
        this.posts = this.posts.filter(post => post.id !== id);
      },
      error: (error: any) => {
        this.error = error.error.message;
      },

    });
  }

}
