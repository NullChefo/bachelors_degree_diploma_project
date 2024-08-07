import {Component, Input} from '@angular/core';
import {AuthService} from 'src/app/services/auth/auth.service';
import {MessageRetrieveDTO} from 'src/types/Message';

@Component({
  selector: 'app-chat-item',
  templateUrl: './chat-item.component.html',
  styleUrls: ['./chat-item.component.css']
})
export class ChatItemComponent {

  @Input() chat!: MessageRetrieveDTO;
  currentUser = this.authService.getUserUsername();

  constructor(private authService: AuthService) {
  }

}
