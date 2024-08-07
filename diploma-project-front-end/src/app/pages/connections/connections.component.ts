import {Component, OnInit} from '@angular/core';
import {ConnectionsService} from 'src/app/services/connections.service';

@Component({
  selector: 'app-connections',
  templateUrl: './connections.component.html',
  styleUrls: ['./connections.component.css']
})
export class ConnectionsComponent implements OnInit {


  constructor(private connectionService: ConnectionsService) {
  }

  ngOnInit(): void {


  }


}
