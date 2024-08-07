import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MediaRetrieveDTO} from 'src/types/Media';

@Component({
  selector: 'app-media-item',
  templateUrl: './media-item.component.html',
  styleUrls: ['./media-item.component.css']
})
export class MediaItemComponent {

  @Input() attachment!: MediaRetrieveDTO;
  @Output() deleteClicked = new EventEmitter<number>();

}
