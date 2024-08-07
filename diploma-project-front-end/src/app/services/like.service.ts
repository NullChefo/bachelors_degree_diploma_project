import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {LIKE_ENDPOINT} from 'src/environments/environment';
import {LikeRetrieveDTO} from 'src/types/Like';
import {AuthService} from './auth/auth.service';
import {Observable} from 'rxjs';
import {UserRetrieveDTO} from 'src/types/User';
import {Page} from 'src/types/Page';

@Injectable({
  providedIn: 'root'
})
export class LikeService {


  currentUser: UserRetrieveDTO | undefined;
  private baseURL = LIKE_ENDPOINT;

  constructor(private http: HttpClient, private authService: AuthService) {

  }

  likeDislikePostById(postId: number): Observable<LikeRetrieveDTO> {

    return this.http.post<LikeRetrieveDTO>(`${this.baseURL}/post/${postId}`, {});
  }


  likeDislikeComment(commentId: number): Observable<LikeRetrieveDTO> {

    return this.http.post<LikeRetrieveDTO>(`${this.baseURL}/comment/${commentId}`, {});
  }

  getLikesForPost(postId: number, page?: number, size?: number): Observable<Page<LikeRetrieveDTO>> {
    // Default values
    if (page == undefined) {
      page = 0;
    }
    if (size == undefined) {
      size = 10;
    }

    let params = new HttpParams();
    params = params.append('page', page.toString());
    params = params.append('size', size.toString());

    const url = `${this.baseURL}/post/${postId}`;
    return this.http.get<Page<LikeRetrieveDTO>>(url, {params});
  }


  getLikesForComment(commentId: number, page?: number, size?: number): Observable<Page<LikeRetrieveDTO>> {
    // Default values
    if (page == undefined) {
      page = 0;
    }
    if (size == undefined) {
      size = 10;
    }
    let params = new HttpParams();
    params = params.append('page', page.toString());
    params = params.append('size', size.toString());

    const url = `${this.baseURL}/comment/${commentId}`;
    return this.http.get<Page<LikeRetrieveDTO>>(url, {params});
  }


}
