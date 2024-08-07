import {HttpEventType, HttpResponse} from '@angular/common/http';
import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {MediaService} from 'src/app/services/media.service';

@Component({
  selector: 'app-media-add',
  templateUrl: './media-add.component.html',
  styleUrls: ['./media-add.component.css']
})
export class MediaAddComponent implements OnInit, OnDestroy {

  selectedFiles?: FileList;
  progressInfos: any[] = [];
  message: string[] = [];

  @Input() postUUID: string | undefined = undefined;

  fileInfos?: Observable<any>;

  constructor(private mediaService: MediaService) {
  }

  ngOnDestroy(): void {

  }

  ngOnInit(): void {
    this.fileInfos = this.mediaService.getFiles();
  }

  selectFiles(event: any): void {
    this.message = [];
    this.progressInfos = [];
    this.selectedFiles = event.target.files;
  }

  uploadFiles(): void {
    this.message = [];

    if (this.selectedFiles) {
      for (let i = 0; i < this.selectedFiles.length; i++) {
        this.upload(i, this.selectedFiles[i]);
      }
    }
  }

  upload(idx: number, file: File): void {

    if (this.postUUID == undefined) {
      return;
    }

    this.progressInfos[idx] = {value: 0, fileName: file.name};

    if (file) {
      this.mediaService.upload(file, this.postUUID).subscribe({
        next: (event: any) => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progressInfos[idx].value = Math.round(100 * event.loaded / event.total);
          } else if (event instanceof HttpResponse) {
            const msg = 'Uploaded the file successfully: ' + file.name;
            this.message.push(msg);
            this.fileInfos = this.mediaService.getFiles();
          }
        },
        error: (err: any) => {
          this.progressInfos[idx].value = 0;
          const msg = 'Could not upload the file: ' + file.name;
          this.message.push(msg);
          this.fileInfos = this.mediaService.getFiles();
        }
      });
    }
  }


}
