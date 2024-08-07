import {Component} from '@angular/core';
import {
  ANALYTICS,
  COMPANY_ADDRESS,
  COMPANY_NAME,
  COMPANY_PHONE,
  DATA_SOURCES,
  LAUNCHED_DATE,
  PROJECT_ACTIVITIES,
  PROJECT_NAME,
  SUPPORT_EMAIL
} from 'src/app/constants/information';

@Component({
  selector: 'app-privacy-policy',
  templateUrl: './privacy-policy.component.html',
  styleUrls: ['./privacy-policy.component.css']
})
export class PrivacyPolicyComponent {
  companyName = COMPANY_NAME;
  projectName = PROJECT_NAME;
  projectActivities = PROJECT_ACTIVITIES;
  dataSources = DATA_SOURCES;
  analytics = ANALYTICS;
  launchedDate = LAUNCHED_DATE;
  companyAddress = COMPANY_ADDRESS;
  supportAddress = SUPPORT_EMAIL;
  companyPhone = COMPANY_PHONE;
}
