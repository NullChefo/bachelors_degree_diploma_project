import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {ConnectionsService} from 'src/app/services/connections.service';
import {ConnectionRetrievalDTO} from 'src/types/Connection';

@Component({
  selector: 'app-connection-list',
  templateUrl: './connection-list.component.html',
  styleUrls: ['./connection-list.component.css']
})
export class ConnectionListComponent implements OnInit, OnDestroy {


  @Input() connections: ConnectionRetrievalDTO[] = [];
  error: string = "";

  constructor(private connectionService: ConnectionsService) {
  }

  ngOnInit(): void {

  }

  onConnectionAccept(connectionId: number) {
    this.connectionService.acceptConnectionRequest(connectionId).subscribe({

      next: () => {
        this.connections = this.connections.filter(connection => connection.id !== connectionId);
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
        this.error = error.error.message;
      }


    })
  }

  onDeleteConnection(connectionId: number) {

    this.connectionService.removeConnection(connectionId).subscribe({

      next: () => {
        this.connections = this.connections.filter(connection => connection.id !== connectionId);
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
        this.error = error.error.message;
      }
    })


  }


  ngOnDestroy(): void {

  }


}
