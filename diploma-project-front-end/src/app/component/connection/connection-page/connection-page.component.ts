import {Component, OnDestroy, OnInit} from '@angular/core';
import {ConnectionsService} from 'src/app/services/connections.service';
import {ConnectionRetrievalDTO} from 'src/types/Connection';
import {Page} from 'src/types/Page';
import {UserRetrieveDTO} from 'src/types/User';
import {addUniqueItems} from '../../profile/view-profile/view-profile.component';

@Component({
  selector: 'app-connection-page',
  templateUrl: './connection-page.component.html',
  styleUrls: ['./connection-page.component.css']
})
export class ConnectionPageComponent implements OnInit, OnDestroy {

  connections: ConnectionRetrievalDTO[] = [];
  requestedConnections: ConnectionRetrievalDTO[] = [];
  suggestedConnections: UserRetrieveDTO[] = [];

  connectionsPage: number = 0;
  connectionsSize: number = 20;
  connectionsError: string = "";

  requestedConnectionsPage: number = 0;
  requestedConnectionsSize: number = 20;
  requestedConnectionsError: string = "";

  suggestedConnectionsPage: number = 0;
  suggestedConnectionsSize: number = 20;
  suggestedConnectionsError: string = "";

  constructor(private connectionService: ConnectionsService) {
  }

  ngOnInit(): void {


    this.connectionService.getAllConnectionsForCurrentUser(this.connectionsPage, this.connectionsSize).subscribe({
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
        this.connectionsError = error.error.message;
      }
    });


    this.connectionService.listConnectionRequests(this.requestedConnectionsPage, this.requestedConnectionsSize).subscribe({
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
        this.requestedConnectionsError = error.error.message;
      }
    });


    this.connectionService.getSuggestionRequests(this.suggestedConnectionsPage, this.suggestedConnectionsSize).subscribe({
      next: (response: Page<UserRetrieveDTO>) => {

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
        this.suggestedConnectionsError = error.error.message;
      }
    });


  }

  ngOnDestroy(): void {
  }


}

