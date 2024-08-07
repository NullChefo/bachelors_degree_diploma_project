import { Component, OnInit } from '@angular/core';

import { faEnvelope } from '@fortawesome/free-regular-svg-icons';

@Component({
  selector: 'app-thanks-for-registration',
  templateUrl: './thanks-for-registration.component.html',
  styleUrls: ['./thanks-for-registration.component.css']
})
export class ThanksForRegistrationComponent implements OnInit {

  faEnvelope = faEnvelope


  constructor() { }

  ngOnInit(): void {
  }

}
