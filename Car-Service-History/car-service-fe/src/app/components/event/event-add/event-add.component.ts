import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { EventService } from 'src/app/services/event.service';
import { JwtService } from 'src/app/services/jwt.service';
import { Car } from 'src/app/types/Car';
import { EventType } from 'src/app/types/Event';

@Component({
  selector: 'app-event-add',
  templateUrl: './event-add.component.html',
  styleUrls: ['./event-add.component.css']
})
export class EventAddComponent implements OnInit, OnDestroy {

  eventId = "";

  carId = "";

  sub: Subscription = new Subscription;

  error = "";


  date = "";

  nextDate = "";




  isFormLegible = true;

  event: EventType = {
    id: 0,
    carId: 0,
    date: new Date("2021-01-01"),
    currentMileage: 0,
    periodicEvent: false,
    body: '',
    nextDate: new Date("2001-01-01"),   // TODO update from field
    changeAfterMileage: 0
  }

  isCarIdValid = true;
  isDateValid = true;
  isCurrentMileage = true;
  isBodValid = true;
  isNextDateValid = true;
  isChangeAfterMilageValid = true;
  isDateLater = true;


  constructor(
    private eventService: EventService,
    private activationRoute: ActivatedRoute,
    private router: Router,
    private jwtService: JwtService
  ) { }

  ngOnInit(): void {



    this.sub = this.activationRoute.paramMap.subscribe(params => {
      var carId = params.get('carId');
      if (carId) {
        this.carId = carId.toString();
        this.event.carId = parseInt(this.carId);
      }

    })


    this.sub = this.activationRoute.paramMap.subscribe(params => {
      var eventId = params.get('eventId');
      if (eventId) {
        this.eventId = eventId.toString();
      }

    })


    if (this.carId == "" || this.carId == undefined || this.carId == null) { // TODO || this.event.id
      this.isFormLegible = false;

    }

    if (this.eventId != "" && this.eventId != undefined && this.eventId != null) { // TODO || this.event.id
      this.isFormLegible = true;

    }




    if (!(this.eventId == null || this.eventId == "" || this.eventId == '' || this.eventId == undefined)) {

      this.eventService.getEvent(this.eventId).subscribe({

        error: (e) => {
          if (e instanceof HttpErrorResponse) {
            switch (e.status) {
              case 400:
                this.error = "Not valid form!";
                break;
              case 401: // Unauthorized
                this.error = "Unauthorized! Please log in again!";
                break;
              default:
                // TODO catch more
                this.error = "There is a unexpected problem please  <a class='underline underline-offset-2' href='mailto:support@nullchefo.com'> contact support! </a> "
                break;
            }
          }
        },
        next: (response: EventType) => {
          this.event = response;

          this.error = "";

          this.date = this.parseDate(this.event.date);
          this.nextDate = this.parseDate(this.event.nextDate);

        },
        complete: () => {

        }, // completeHandler
      });
    }
    else {

      this.eventId = "";

    }

  }

  parseDate(date: any) {

    var date1 = new Date(date);

    //  date1 =  date1.toISOString().split('T')[0]; 


    return date1.toISOString().split('T')[0];
  }


  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }




  validateForm() {

    const invalidCarId = this.event.carId == null || this.event.carId == 0;

    console.log(this.carId)
    if (invalidCarId) {
      this.isCarIdValid = false;

    }
    else {
      this.isCarIdValid = true;
    }


    const invalidDate = this.event.date == null || this.event.date == undefined || this.event.nextDate == new Date(1, 1, 1, 1); // || date < 1900 year

    if (invalidDate) {
      this.isDateValid = false;
    }
    else {
      this.isDateValid = true;
    }


    const invalidCurrentMileage = this.event.currentMileage == null || this.event.currentMileage == 0;

    if (invalidCurrentMileage) {
      this.isCurrentMileage = false;
    }
    else {
      this.isCurrentMileage = true;
    }



    const invalidBody = this.event.body == null || this.event.body == "";

    if (invalidBody) {
      this.isBodValid = false;
    }
    else {
      this.isBodValid = true;
    }



    //changeAfterMileage

    const invalidNextDate = this.event.nextDate == null || this.event.nextDate == undefined || this.event.nextDate == new Date(1, 1, 1, 1); // || date > 3000

    if (invalidNextDate) {
      this.isNextDateValid = false;
    }
    else {
      this.isNextDateValid = true;
    }


    const invalidChangeAfterMilage = this.event.changeAfterMileage == null || this.event.changeAfterMileage == undefined || this.event.changeAfterMileage == 0;  // || date > 3000

    if (invalidChangeAfterMilage) {
      this.isChangeAfterMilageValid = false;
    }
    else {
      this.isChangeAfterMilageValid = true;
    }

    const invalidNextDateCalculated = new Date(this.nextDate) < new Date(this.date);  // || date > 3000

    if (invalidNextDateCalculated) {
      this.isDateLater = false;
    }
    else {
      this.isDateLater = true;
    }



  }

  isFormValid(): boolean {
    return this.isCarIdValid && this.isDateValid && this.isCurrentMileage && this.isBodValid && this.isNextDateValid && this.isChangeAfterMilageValid;
  }



  submit() {

    this.validateForm();

    if (!this.isFormValid()) {
      this.error = "Invalid Form"
      return;
    }


    if (this.event.carId == null || this.event.carId == 0 || this.event.carId == undefined) {

      this.error = "Cannot add event without selecting car";
      return;

    }

    this.event.date = new Date(this.date);
    this.event.nextDate = new Date(this.nextDate);


    if (this.eventId == null || this.eventId == "" || this.eventId == undefined) {

      this.eventService.createEvent(this.event).subscribe({

        error: (e) => {
          if (e instanceof HttpErrorResponse) {
            switch (e.status) {

              case 400: // Unauthorized
                this.error = "Not valid form!";
                break;

              case 401: // Unauthorized
                this.error = "Unauthorized! Please log in again!";
                break;
              default:
                // TODO catch more
                this.error = "There is a unexpected problem please  <a class='underline underline-offset-2' href='mailto:support@nullchefo.com'> contact support! </a> "
                break;
            }
          }
        },
        next: (response) => {
          this.error = "";
          this.router.navigate([`/cars/user`]);

        },
        complete: () => {

        }, // completeHandler
      });


      // post



    } else {

      this.eventService.updateEvent(this.event).subscribe({

        error: (e) => {
          if (e instanceof HttpErrorResponse) {
            switch (e.status) {

              case 400: // Unauthorized
                this.error = "Not valid form!";
                break;

              case 401: // Unauthorized
                this.error = "Unauthorized! Please log in again!";
                break;
              default:
                // TODO catch more
                this.error = "There is a unexpected problem please  <a class='underline underline-offset-2' href='mailto:support@nullchefo.com'> contact support! </a> "
                break;
            }
          }
        },
        next: (response) => {
          this.error = "";
          this.router.navigate([`/cars/user`]);

        },
        complete: () => {

        }, // completeHandler
      });





      // put
    }


  }
}
