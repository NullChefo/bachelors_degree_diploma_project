import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {USER_PATH_V2} from 'src/environments/environment';
import {EditUserDTO, ProfileRetrieveDTO} from 'src/types/User';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {


  API_URL: string = USER_PATH_V2;

  constructor(private http: HttpClient) {
  }


  getUser(): Observable<EditUserDTO> {
    return this.http.get<EditUserDTO>(`${this.API_URL}`);
  }

  updateUser(user: EditUserDTO): Observable<EditUserDTO> {
    return this.http.post<EditUserDTO>(this.API_URL, user);
  }

  getProfile(userId: number): Observable<ProfileRetrieveDTO> {

    if (userId == null || userId == undefined || userId == 0) {
      return this.http.get<ProfileRetrieveDTO>(`${this.API_URL}/profile`);
    } else {
      return this.http.get<ProfileRetrieveDTO>(`${this.API_URL}/profile/${userId}`);
    }
  }

  getProfileByUsername(username?: string): Observable<ProfileRetrieveDTO> {
    if (username == "" || username == null || username == undefined) {
      return this.http.get<ProfileRetrieveDTO>(`${this.API_URL}/profile`);
    } else {
      return this.http.get<ProfileRetrieveDTO>(`${this.API_URL}/profile/username/${username}`);
    }


  }


  deleteProfileByUsername(username: string) {
    return this.http.delete<void>(`${this.API_URL}/${username}`);
  }


}
