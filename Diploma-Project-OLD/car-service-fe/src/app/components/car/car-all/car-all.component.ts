import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { CarService } from 'src/app/services/car.service';
import { Car } from 'src/app/types/Car';

@Component({
  selector: 'app-car-all',
  templateUrl: './car-all.component.html',
  styleUrls: ['./car-all.component.css']
})
export class CarAllComponent implements OnInit, OnDestroy {
  sub: Subscription = new Subscription;

  userId = "";
  // TODO add pagination

  isLoaded = false;
  error = "";
  cars: Car[] = [];




  constructor(private carService: CarService,
    private activationRoute: ActivatedRoute,
    private router: Router

  ) { }


  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }



  ngOnInit(): void {

    this.sub = this.activationRoute.paramMap.subscribe(params => {
      var userId = params.get('userId');
      if (userId) {
        this.userId = userId.toString();
      }
    })

    console.log(this.userId)

    if (this.userId == "" || this.userId == undefined || this.userId == null) { // TODO || this.event.id


      this.carService.getAll().subscribe({

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
                this.error = "No cars found";
                break;

              default:
                // TODO catch more
                this.error = "There is a unexpected problem please  <a class='underline underline-offset-2' href='mailto:support@nullchefo.com'> contact support! </a> "
                break;
            }
          }
        },
        next: (response: Car[]) => {


          this.cars = response;

          console.log(response)  // todo remove


        },
        complete: () => {


          this.isLoaded = true;

        }, // completeHandler

      });

    }

    else if (this.userId == "user") { // TODO || this.event.id

      this.carService.getCarsForCurrentUser().subscribe({

        error: (e) => {
          if (e instanceof HttpErrorResponse) {
            switch (e.status) {
              case 401: // Unauthorized
                this.error = "Unauthorized! Please log in again!";
                break;
              case 404: // No elements
                this.error = "No cars found";

                break;


              default:
                // TODO catch more
                this.error = "There is a unexpected problem please  <a class='underline underline-offset-2' href='mailto:support@nullchefo.com'> contact support! </a> "
                break;
            }
          }
        },
        next: (response: Car[]) => {

          this.cars = response;
          console.log(response)  // todo remove
        },
        complete: () => {


          this.isLoaded = true;

        }, // completeHandler

      });
    }
    else {


      this.carService.getCarsForUserId(this.userId).subscribe({

        error: (e) => {
          if (e instanceof HttpErrorResponse) {
            switch (e.status) {
              case 401: // Unauthorized
                this.error = "Unauthorized! Please log in again!";
                break;
              case 404: // No elements
                this.error = "No cars found";
                break;

              default:
                // TODO catch more
                this.error = "There is a unexpected problem please  <a class='underline underline-offset-2' href='mailto:support@nullchefo.com'> contact support! </a> "
                break;
            }
          }
        },
        next: (response: Car[]) => {

          this.cars = response;
          console.log(response)  // todo remove
        },
        complete: () => {


          this.isLoaded = true;

        }, // completeHandler

      });
    }
  }

  delete(car: Car): void {

    this.carService.deleteCar(car.id).subscribe({

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
    this.cars = this.cars.filter(function (item) {
      return item !== car;


    })

  }



  edit(carId: number) {

    var navigateTo = "/car/add/" + carId;

    console.log(navigateTo);

    this.router.navigate([navigateTo]);

  }

  addEvent(carId: number) {
    var navigateTo = "/event/" + carId;
    this.router.navigate([navigateTo]);
  }

  viewEvents(carId: number) {
    var navigateTo = "/events/" + carId;
    this.router.navigate([navigateTo]);

  }

}
