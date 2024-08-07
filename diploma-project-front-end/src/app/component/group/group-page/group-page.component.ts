import {Component} from '@angular/core';
import {GroupRetrieveDTO} from 'src/types/Group';

@Component({
  selector: 'app-group-page',
  templateUrl: './group-page.component.html',
  styleUrls: ['./group-page.component.css']
})
export class GroupPageComponent {

  groups: GroupRetrieveDTO[] = [];

}
