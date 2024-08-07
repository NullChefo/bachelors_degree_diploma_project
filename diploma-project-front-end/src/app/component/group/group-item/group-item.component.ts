import {Component, Input} from '@angular/core';
import {GroupRetrieveDTO} from 'src/types/Group';

@Component({
  selector: 'app-group-item',
  templateUrl: './group-item.component.html',
  styleUrls: ['./group-item.component.css']
})
export class GroupItemComponent {
  @Input() group!: GroupRetrieveDTO;

}
