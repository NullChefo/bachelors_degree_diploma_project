import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {MediaRetrieveDTO} from 'src/types/Media';


@Component({
  selector: 'app-media-list',
  templateUrl: './media-list.component.html',
  styleUrls: ['./media-list.component.css']
})
export class MediaListComponent implements OnInit, OnDestroy {

  @Input() attachments!: MediaRetrieveDTO[] | undefined;

  constructor() {
  }

  ngOnInit(): void {
    if (this.attachments == undefined) {
      return;
    }
  }

  ngOnDestroy(): void {
  }

  onDelete(id: number): void {
    // this.mediaService.deleteMedia(id).subscribe({
    //     next: () => {
    //         this.posts = this.posts.filter(post => post.id !== id);
    //     }
    // });
  }

}
