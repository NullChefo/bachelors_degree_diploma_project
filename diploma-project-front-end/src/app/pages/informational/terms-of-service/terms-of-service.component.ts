import {Component} from '@angular/core';
import {COMPANY_NAME, PROJECT_NAME} from 'src/app/constants/information';

@Component({
  selector: 'app-terms-of-service',
  templateUrl: './terms-of-service.component.html',
  styleUrls: ['./terms-of-service.component.css']
})
export class TermsOfServiceComponent {
  companyName = COMPANY_NAME;
  projectName = PROJECT_NAME;
}
