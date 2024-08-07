import { Component } from '@angular/core';
import { faCar, faHomeAlt, faPlus,faClock } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-left-panel',
  templateUrl: './left-panel.component.html',
  styleUrls: ['./left-panel.component.css']
})
export class LeftPanelComponent {
  faHomeAlt = faHomeAlt;
  faCar = faCar;
  faAdd = faPlus;
  faEvents = faClock;

  constructor() { }

  ngOnInit(): void {
  }

}
