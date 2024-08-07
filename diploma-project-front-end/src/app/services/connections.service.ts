import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {CONNECTION_ENDPOINT} from 'src/environments/environment';
import {ConnectionRetrievalDTO} from 'src/types/Connection';
import {Page} from 'src/types/Page';
import {UserRetrieveDTO} from 'src/types/User';

@Injectable({
  providedIn: 'root'
})
export class ConnectionsService {

  private readonly baseURL = CONNECTION_ENDPOINT;


  constructor(private http: HttpClient) {
  }

  getAllConnectionsForCurrentUser(page?: number, size?: number): Observable<Page<ConnectionRetrievalDTO>> {
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

    const url = `${this.baseURL}`;
    return this.http.get<Page<ConnectionRetrievalDTO>>(url, {params});
  }


  getConnectionById(id: number): Observable<ConnectionRetrievalDTO> {
    const url = `${this.baseURL}/${id}`;
    return this.http.get<ConnectionRetrievalDTO>(url);
  }

  sendConnectionRequest(id: number): Observable<any> {
    const url = `${this.baseURL}/request/${id}`;
    return this.http.post(url, {});
  }

  acceptConnectionRequest(id: number): Observable<any> {
    const url = `${this.baseURL}/accept/${id}`;
    return this.http.post(url, {});
  }


  removeConnection(connectionId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseURL}/${connectionId}`);
  }


  listConnectionRequests(page?: number, size?: number): Observable<Page<ConnectionRetrievalDTO>> {

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

    const url = `${this.baseURL}/list`;
    return this.http.get<Page<ConnectionRetrievalDTO>>(url, {params});
  }


  getSuggestionRequests(page?: number, size?: number) {

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

    const url = `${this.baseURL}/suggestions`;
    return this.http.get<Page<UserRetrieveDTO>>(url, {params});

  }


}
