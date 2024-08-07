import {Component, Input} from '@angular/core';
import {LikeRetrieveDTO} from 'src/types/Like';

@Component({
  selector: 'app-like-item',
  templateUrl: './like-item.component.html',
  styleUrls: ['./like-item.component.css']
})
export class LikeItemComponent {

  @Input() like: LikeRetrieveDTO = {};
}
