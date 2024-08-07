import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ConnectionRetrievalDTO} from 'src/types/Connection';

@Component({
  selector: 'app-connection-item',
  templateUrl: './connection-item.component.html',
  styleUrls: ['./connection-item.component.css']
})
export class ConnectionItemComponent {


  @Input() connection!: ConnectionRetrievalDTO;
  @Output() acceptConnectionEvent = new EventEmitter<number>();
  @Output() deleteConnectionEvent = new EventEmitter<number>();

  acceptConnection(connectionId: number | undefined) {
    if (connectionId != undefined) {
      this.acceptConnectionEvent.emit(connectionId)
    }

  }

  deleteConnection(connectionId: number | undefined) {
    if (connectionId != undefined) {
      this.deleteConnectionEvent.emit(connectionId)
    }
  }


}
