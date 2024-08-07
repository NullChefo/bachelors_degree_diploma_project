import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Page} from 'src/types/Page';
import {POST_ENDPOINT} from 'src/environments/environment';
import {PostCreateDTO, PostRetrieveDTO} from 'src/types/Post';


@Injectable({
  providedIn: 'root'
})
export class PostService {


  private readonly API_URL = POST_ENDPOINT;

  constructor(private http: HttpClient) {
  }


  getAllPostsPageable(page: number = 0, size: number = 10, content?: string): Observable<Page<PostRetrieveDTO>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    if (content) {
      params = params.set('content', content);
    }
    return this.http.get<Page<PostRetrieveDTO>>(this.API_URL, {params});
  }


  getAllPostsForGroupPageableNew(
    groupId: number,
    page: number = 0,
    size: number = 10,
    content?: string
  ): Observable<Page<PostRetrieveDTO>> {
    const params = new HttpParams()
      .set('page', String(page))
      .set('size', String(size));

    if (content) {
      params.set('content', content);
    }

    return this.http.get<Page<PostRetrieveDTO>>(`${this.API_URL}/group/${groupId}`, {params});
  }


  getPostsByForUsername(username: string, page: number, size: number): Observable<Page<PostRetrieveDTO>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<Page<PostRetrieveDTO>>(`${this.API_URL}/user/${username}`, {params});
  }


  createPost(post: PostCreateDTO): Observable<PostRetrieveDTO> {
    return this.http.post<PostRetrieveDTO>(this.API_URL, post);
  }


  updatePost(post: PostRetrieveDTO): Observable<PostRetrieveDTO> {
    return this.http.put<PostRetrieveDTO>(`${this.API_URL}/${post.id}`, post);
  }

  deletePost(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/${id}`);
  }

  getPostById(postId: number): Observable<PostRetrieveDTO> {
    return this.http.get<PostRetrieveDTO>(`${this.API_URL}/${postId}`);
  }


}



