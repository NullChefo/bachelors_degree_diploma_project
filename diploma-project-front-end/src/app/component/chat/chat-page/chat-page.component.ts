import {Component, OnDestroy, OnInit} from '@angular/core';
import {ChatService} from 'src/app/services/chat.service';
import {ConnectionsService} from 'src/app/services/connections.service';
import {GroupService} from 'src/app/services/group.service';
import {GroupRetrieveDTO} from 'src/types/Group';
import {Page} from 'src/types/Page';
import {ConnectionRetrievalDTO} from 'src/types/Connection';
import {MessageRetrieveDTO} from 'src/types/Message';
import {addUniqueItems} from '../../profile/view-profile/view-profile.component';

@Component({
  selector: 'app-chat-page',
  templateUrl: './chat-page.component.html',
  styleUrls: ['./chat-page.component.css']
})
export class ChatPageComponent implements OnInit, OnDestroy {

  groups: GroupRetrieveDTO[] = [];
  groupError: string = '';
  groupPageSize: number = 20;
  groupPageNumber: number = 0;
  connections: ConnectionRetrievalDTO[] = [];
  connectionError: string = '';
  connectionPageSize: number = 20;
  connectionPageNumber: number = 0;
  chats: MessageRetrieveDTO[] = [];
  chatError: string = '';
  chatPageSize: number = 20;
  chatPageNumber: number = 0;

  constructor(private chatService: ChatService, private groupService: GroupService, private connectionsService: ConnectionsService) {
  }

  ngOnInit(): void {

    this.groupService.getAllGroupsForCurrentUser(this.groupPageSize, this.groupPageNumber).subscribe({
      next: (response: Page<GroupRetrieveDTO>) => {

        if (response.content != undefined) {
          if (this.groups == undefined || this.groups.length == 0) {
            this.groups = response.content;
          } else {
            addUniqueItems(this.groups, response.content);
          }

        } else {
          this.groups = [];
        }

      },
      error: (error: any) => {
        if (error.status === 400) {
          // Handle bad request error
        } else if (error.status === 401) {
          // Handle unauthorized error
        } else if (error.status === 404) {
          // Handle not found error
        } else {
          // Handle other errors
        }
        this.groupError = error.error.message;
      }
    });


    this.connectionsService.getAllConnectionsForCurrentUser(this.connectionPageNumber, this.connectionPageSize).subscribe({
      next: (response: Page<ConnectionRetrievalDTO>) => {

        if (response.content != undefined) {
          if (this.connections == undefined || this.connections.length == 0) {
            this.connections = response.content;
          } else {
            addUniqueItems(this.connections, response.content);
          }

        } else {
          this.connections = [];
        }


      },
      error: (error: any) => {
        if (error.status === 400) {
          // Handle bad request error
        } else if (error.status === 401) {
          // Handle unauthorized error
        } else if (error.status === 404) {
          // Handle not found error
        } else {
          // Handle other errors
        }
        this.connectionError = error.error.message;
      }
    });


    this.chatService.getAllChatsForCurrentUser(this.chatPageNumber, this.chatPageSize).subscribe({
      next: (response: Page<MessageRetrieveDTO>) => {

        if (response.content != undefined) {
          if (this.chats == undefined || this.chats.length == 0) {
            this.chats = response.content;
          } else {
            addUniqueItems(this.chats, response.content);
          }

        } else {
          this.chats = [];
        }


      },
      error: (error: any) => {
        if (error.status === 400) {
          // Handle bad request error
        } else if (error.status === 401) {
          // Handle unauthorized error
        } else if (error.status === 404) {
          // Handle not found error
        } else {
          // Handle other errors
        }
        this.connectionError = error.error.message;
      }
    });

    console.log(this.chats);
  }

  ngOnDestroy(): void {

  }

}



