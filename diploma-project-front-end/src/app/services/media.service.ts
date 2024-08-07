import {HttpClient, HttpEvent, HttpRequest, HttpResponse} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {MEDIA_ENDPOINT} from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MediaService {

  private baseURL = MEDIA_ENDPOINT;

  constructor(private http: HttpClient) {
  }

  uploadFile(file: File, postUUID: string): Promise<any> {
    const formData = new FormData();
    formData.append('file', file, file.name);
    formData.append('postUUID', postUUID);

    return this.http.post<any>(this.baseURL, formData)
      .toPromise()
      .then(response => {
        return `Created file id: ${response}`;
      })
      .catch(error => {
        return `Exited with exception: ${error}`;
      });
  }

  downloadFile(id: number): Observable<HttpResponse<Blob>> {
    return this.http.get(`${this.baseURL}${id}`, {
      observe: 'response',
      responseType: 'blob'
    });
  }


  deleteFile(id: number): Promise<any> {
    return this.http.delete<any>(`${this.baseURL}${id}`)
      .toPromise()
      .then(() => {
        return `File with id ${id} deleted successfully.`;
      })
      .catch(error => {
        return `Failed to delete file with id ${id}: ${error}`;
      });
  }


  // https://www.bezkoder.com/angular-14-multiple-file-upload/

  upload(file: File, postUUID: string): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    formData.append('file', file);
    formData.append('postUUID', postUUID);

    const req = new HttpRequest('POST', `${this.baseURL}/post`, formData, {
      reportProgress: true
    });

    return this.http.request(req);
  }


  getFiles(): Observable<any> {
    return this.http.get(`${this.baseURL}`);
  }


}
