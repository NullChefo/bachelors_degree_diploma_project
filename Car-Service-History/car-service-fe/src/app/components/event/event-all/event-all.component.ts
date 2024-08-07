import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { EventService } from 'src/app/services/event.service';
import { EventType } from 'src/app/types/Event';


@Component({
  selector: 'app-event-all',
  templateUrl: './event-all.component.html',
  styleUrls: ['./event-all.component.css']
})
export class EventAllComponent implements OnInit, OnDestroy {

  sub: Subscription = new Subscription;
  carId = "";



  isLoaded = false;
  error = "";
  events: EventType[] = [];


  constructor(
    private eventService: EventService,
    private activationRoute: ActivatedRoute,
    private router: Router
  ) { }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  ngOnInit(): void {

    this.sub = this.activationRoute.paramMap.subscribe(params => {
      var carId = params.get('carId');
      if (carId) {
        this.carId = carId.toString();
      }
    })

    if (this.carId == "" || this.carId == undefined || this.carId == null) { // TODO || this.event.id


      this.eventService.getAll().subscribe({

        error: (e) => {
          if (e instanceof HttpErrorResponse) {
            switch (e.status) {
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
        next: (events: EventType[]) => {
          this.events = events;
        },
        complete: () => {
          this.isLoaded = true;
          this.error = "";

        }, // completeHandler

      });
    }
    else {

      this.eventService.getEventsForCar(this.carId).subscribe({

        error: (e) => {
          if (e instanceof HttpErrorResponse) {
            switch (e.status) {

              case 400: // Unauthorized
                this.error = "Not valid form!";
                break;
              case 401: // Unauthorized
                this.error = "Unauthorized! Please log in again!";
                break;
              case 404: // No elements
                this.error = "No event for this car!";

                break;


              default:
                // TODO catch more
                this.error = "There is a unexpected problem please  <a class='underline underline-offset-2' href='mailto:support@nullchefo.com'> contact support! </a> "
                break;
            }
          }
        },
        next: (events: EventType[]) => {
          this.events = events;
        },
        complete: () => {
          this.isLoaded = true;
          this.error = "";

        }, // completeHandler
      });

    }

  }


  deleteEvent(event: EventType) {
    this.eventService.deleteEvent(event.id).subscribe({

      error: (e) => {
        if (e instanceof HttpErrorResponse) {
          switch (e.status) {
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
      },
      complete: () => {
        this.isLoaded = true;
      }
    })

    //EAGER delete
    this.events = this.events.filter(function (item) {
      return item !== event;
    })

  }



  editEvent(eventId: number) {

    var navigateTo = "/event/edit/" + eventId;
    this.router.navigate([navigateTo]);
  }


}
