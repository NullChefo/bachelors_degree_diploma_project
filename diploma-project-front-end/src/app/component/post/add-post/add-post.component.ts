import {HttpErrorResponse} from '@angular/common/http';
import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from 'src/app/services/auth/auth.service';
import {PostService} from 'src/app/services/post.service';
import {PostCreateDTO, PostRetrieveDTO} from 'src/types/Post';

@Component({
  selector: 'app-edit-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.css']
})
export class AddPostComponent implements OnInit, OnDestroy {

  post: PostCreateDTO = {
    content: "",
    postUUID: ""

  }

  @Output() postAdded = new EventEmitter<PostRetrieveDTO>();


  // currentUser: UserRetrieveDTO | undefined;
  // sub: Subscription = new Subscription;

  error = "";

  postId: number | null = null;

  constructor(private postService: PostService, private activationRoute: ActivatedRoute,
              private router: Router, private authService: AuthService) {
  }


  createUUID() {
    // http://www.ietf.org/rfc/rfc4122.txt
    var s: any = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
      s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";

    var uuid = s.join("");
    this.post.postUUID = uuid;
  }


  ngOnInit(): void {
    this.createUUID();

    // this.getCurrentUser();

    // this.sub = this.activationRoute.paramMap.subscribe(params => {
    //     var value = params.get('postId');
    //     if (value) {
    //         this.postId = Number(value);
    //     }

    // })


    // if (!(this.postId == null || this.postId == 0 || this.postId == undefined)) {

    //     this.postService.getPostById(this.postId).subscribe({

    //         error: (e: any) => {
    //             if (e instanceof HttpErrorResponse) {
    //                 switch (e.status) {
    //                     case 400:
    //                         this.error = "Not valid form!";
    //                         break;
    //                     case 401: // Unauthorized
    //                         this.error = "Unauthorized! Please log in again!";
    //                         break;
    //                     default:
    //                         // TODO catch more
    //                         this.error = "There is a unexpected problem please  <a class='underline underline-offset-2' href='mailto:support@nullchefo.com'> contact support! </a> "
    //                         break;
    //                 }
    //             }
    //         },
    //         next: (response: PostRetrieveDTO) => {
    //             this.post = response;

    //         },
    //         complete: () => {

    //         },
    //     });
    // } else {

    //     this.postId = null;
    // }


  }

  // editPost(post: PostRetrieveDTO){
  //     this.postService.updatePost(post).subscribe({
  //         next: (response: PostRetrieveDTO) => {

  //         },
  //         error: (error: any) => {
  //             if (error.status === 400) {
  //                 // Handle bad request error
  //             } else if (error.status === 401) {
  //                 // Handle unauthorized error
  //             } else if (error.status === 404) {
  //                 // Handle not found error
  //             } else {
  //                 // Handle other errors
  //             }
  //         }
  //     });

  // }

  // createPost(post: PostCreateDTO){
  //     this.postService.createPost(post).subscribe({
  //         next: (response: PostRetrieveDTO) => {

  //         },
  //         error: (error: any) => {
  //             if (error.status === 400) {
  //                 // Handle bad request error
  //             } else if (error.status === 401) {
  //                 // Handle unauthorized error
  //             } else if (error.status === 404) {
  //                 // Handle not found error
  //             } else {
  //                 // Handle other errors
  //             }
  //         }
  //     });


  // }

  ngOnDestroy(): void {
    //  this.sub.unsubscribe();
  }


  // getCurrentUser():void{
  //     this.authService.getCurrentUserRestCall().subscribe({
  //         next: (response: UserRetrieveDTO) => {
  //             // TODO see if works
  //           this.currentUser = response;
  //         },
  //         error: (error: HttpErrorResponse) => {
  //             console.log("Error: " + error);
  //             this.currentUser = undefined;

  //         }
  //     })
  // }


  submit() {
    // if (this.postId == null || this.postId == 0 || this.postId == undefined) {


    //     console.log(this.currentUser);

    //     if (this.currentUser == undefined) {

    //         console.log("WTF");
    //         this.error = "No valid user";
    //         return;
    //     }

    // }


    this.postService.createPost(this.post).subscribe({

      error: (e) => {
        if (e instanceof HttpErrorResponse) {
          switch (e.status) {
            case 400: // Unauthorized
              this.error = "Not valid form!";
              break;
            case 401: // Unauthorized
              this.error = "Unauthorized! Please log in again!";
              break;
            case 404: // Unauthorized
              this.error = "No post found!";
              break;

            case 422: // Unprocessable entity
              this.error = "Cannot write more then 500 symbols";
              break;


            default:
              // TODO catch more
              this.error = "There is a unexpected problem please  <a class='underline underline-offset-2' href='mailto:support@nullchefo.com'> contact support! Error message: " + e.error + " </a>"
              break;
          }
        }
      },
      next: (response: PostRetrieveDTO) => {
        this.postAdded.emit(response);
        this.post.content = "";
      },
      complete: () => {
        this.createUUID();
      },
    });
    // post


    // this.postService.updatePost(this.post).subscribe({
    //     error: (e) => {
    //         if (e instanceof HttpErrorResponse) {
    //             switch (e.status) {

    //                 case 400: // Unauthorized
    //                     this.error = "Not valid form!";
    //                     break;

    //                 case 401: // Unauthorized
    //                     this.error = "Unauthorized! Please log in again!";
    //                     break;
    //                 case 404: // Unauthorized
    //                     this.error = "No post found!";
    //                     break;
    //                 default:
    //                     // TODO catch more
    //                     this.error = "There is a unexpected problem please  <a class='underline underline-offset-2' href='mailto:support@nullchefo.com'> contact support! </a> "
    //                     break;
    //             }
    //         }
    //     },
    //     next: (response) => {
    //         console.log("Updated post: " + response);
    //         this.router.navigate([`/cars/user`]);


    //     },
    //     complete: () => {

    //         console.log("errors: " + this.error)

    //     }, // completeHandler


    // });


    // put
  }


}



