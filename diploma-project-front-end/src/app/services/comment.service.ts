import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {COMMENT_ENDPOINT} from 'src/environments/environment';
import {CommentCreateDTO, CommentRetrieveDTO} from 'src/types/Comment';
import {Page} from 'src/types/Page';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private baseUrl = COMMENT_ENDPOINT;

  constructor(private http: HttpClient) {
  }

  getCommentsForPost(postId: number, page?: number, size?: number): Observable<Page<CommentRetrieveDTO>> {
    // Default values
    if (page == undefined) {
      page = 0;
    }
    if (size == undefined) {
      size = 10;
    }

    return this.http.get<Page<CommentRetrieveDTO>>(`${this.baseUrl}/post/${postId}`);
  }

  createCommentForPostId(newComment: CommentCreateDTO): Observable<CommentRetrieveDTO> {
    return this.http.post<CommentRetrieveDTO>(`${this.baseUrl}`, newComment);
  }


}


