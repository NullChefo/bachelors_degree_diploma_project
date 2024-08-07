import {Component, Input} from '@angular/core';
import {MessageRetrieveDTO} from 'src/types/Message';

@Component({
  selector: 'app-chat-list',
  templateUrl: './chat-list.component.html',
  styleUrls: ['./chat-list.component.css']
})
export class ChatListComponent {

  @Input() chats: MessageRetrieveDTO[] = [];

}
