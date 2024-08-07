import {Component, Input} from '@angular/core';
import {GroupRetrieveDTO} from 'src/types/Group';

@Component({
  selector: 'app-group-list',
  templateUrl: './group-list.component.html',
  styleUrls: ['./group-list.component.css']
})
export class GroupListComponent {
  @Input() groups: GroupRetrieveDTO[] = [];

}
