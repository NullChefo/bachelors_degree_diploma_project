import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {MESSAGE_ENDPOINT} from 'src/environments/environment';
import {MessageCreateDTO, MessageRetrieveDTO} from 'src/types/Message';
import {Page} from 'src/types/Page';

@Injectable({
  providedIn: 'root'
})
export class ChatService {


  private baseUrl = MESSAGE_ENDPOINT;

  constructor(private http: HttpClient) {
  }


  getAllChatsForCurrentUser(page?: number, size?: number): Observable<Page<MessageRetrieveDTO>> {
    // Default values
    if (page == undefined) {
      page = 0;
    }
    if (size == undefined) {
      size = 10;
    }
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<Page<MessageRetrieveDTO>>(this.baseUrl, {params});
  }

  getMessagesByUserId(userId: number): Observable<Page<MessageRetrieveDTO>> {
    const url = `${this.baseUrl}/${userId}`;
    return this.http.get<Page<MessageRetrieveDTO>>(url);
  }

  createMessage(message: MessageCreateDTO): Observable<MessageRetrieveDTO> {
    return this.http.post<MessageRetrieveDTO>(this.baseUrl, message);
  }

  deleteMessage(id: number): Observable<void> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.delete<void>(url);
  }


}

