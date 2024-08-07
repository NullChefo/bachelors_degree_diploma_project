import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Page} from 'src/types/Page';
import {GroupCreateDTO, GroupRetrieveDTO} from 'src/types/Group';
import {GROUP_ENDPOINT} from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class GroupService {

  private groupsUrl = GROUP_ENDPOINT;

  constructor(private http: HttpClient) {
  }

  getAllGroupsForCurrentUser(page?: number, size?: number): Observable<Page<GroupRetrieveDTO>> {
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
    return this.http.get<Page<GroupRetrieveDTO>>(this.groupsUrl, {params});
  }

  deleteGroup(id: number): Observable<void> {
    return this.http.delete<void>(`${this.groupsUrl}/${id}`);
  }

  updateGroup(id: number, group: GroupRetrieveDTO): Observable<any> {
    const url = `${this.groupsUrl}/${id}`;
    return this.http.put(url, group);
  }

  createGroup(group: GroupCreateDTO): Observable<GroupRetrieveDTO> {
    return this.http.post<GroupRetrieveDTO>(this.groupsUrl, group);
  }

  findByName(name: string, page: number = 0, size: number = 10): Observable<Page<GroupRetrieveDTO>> {
    const params = new HttpParams()
      .set('name', name)
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', 'createdAt,desc');
    return this.http.get<Page<GroupRetrieveDTO>>(`${this.groupsUrl}/name`, {params});
  }


}





