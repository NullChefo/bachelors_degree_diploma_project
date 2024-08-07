import {Component} from '@angular/core';
import {
  ANALYTICS,
  COMPANY_NAME,
  DATA_SOURCES,
  LAUNCHED_DATE,
  PROJECT_ACTIVITIES,
  PROJECT_NAME
} from 'src/app/constants/information';

@Component({
  selector: 'app-disclaimers',
  templateUrl: './disclaimers.component.html',
  styleUrls: ['./disclaimers.component.css']
})
export class DisclaimersComponent {
  companyName = COMPANY_NAME;
  projectName = PROJECT_NAME;
  projectActivities = PROJECT_ACTIVITIES;
  dataSources = DATA_SOURCES;
  analytics = ANALYTICS;
  launchedDate = LAUNCHED_DATE;


}
