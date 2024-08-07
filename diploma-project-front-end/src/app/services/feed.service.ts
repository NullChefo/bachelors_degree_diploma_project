import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {PostRetrieveDTO} from 'src/types/Post';
import {Page} from 'src/types/Page';
import {FEED_ENDPOINT} from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FeedService {

  private baseUrl = FEED_ENDPOINT;

  constructor(private http: HttpClient) {
  }

  getFeed(page: number = 0, size: number = 10): Observable<Page<PostRetrieveDTO>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<Page<PostRetrieveDTO>>(this.baseUrl, {params});
  }
}
