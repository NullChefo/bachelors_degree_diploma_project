import { Component, OnInit } from '@angular/core';
import { faHomeAlt, faCar, faPlus, faClock } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-left-panel',
  templateUrl: './left-panel.component.html',
  styleUrls: ['./left-panel.component.css']
})
export class LeftPanelComponent implements OnInit {
  faHomeAlt = faHomeAlt;
  faCar = faCar;
  faAdd = faPlus;

  faEvents = faClock;

  constructor() { }

  ngOnInit(): void {
  }

}
