import {Component, Input} from '@angular/core';
import {UserRetrieveDTO} from 'src/types/User';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent {

  @Input() user: UserRetrieveDTO[] = []

}
