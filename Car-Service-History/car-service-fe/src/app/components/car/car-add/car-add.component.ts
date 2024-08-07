import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { CarService } from 'src/app/services/car.service';
import { JwtService } from 'src/app/services/jwt.service';
import { Car } from 'src/app/types/Car';

@Component({
  selector: 'app-car-add',
  templateUrl: './car-add.component.html',
  styleUrls: ['./car-add.component.css']
})
export class CarAddComponent implements OnInit {




  sub: Subscription = new Subscription;

  error = "";

  carId: string = '';

  isCarNameValid = true;

  car: Car = {
    id: 0,
    name: '',
    manufacturer: '',
    model: '',
    year: '',
    plateNumber: '',
    userId: 0
  }

  isNameValid = true;
  isManufacturerValid = true;
  isModelValid = true;
  isYearValid = true;
  isPlateNumberValid = true;

  constructor(
    private jwtService: JwtService,
    private carService: CarService,
    private activationRoute: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {

    this.sub = this.activationRoute.paramMap.subscribe(params => {
      var error = params.get('carId');
      if (error) {
        this.carId = error.toString();
      }

    })

    if (!(this.carId == null || this.carId == "" || this.carId == '' || this.carId == undefined)) {

      this.carService.getCar(this.carId).subscribe({

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
        next: (response: Car) => {
          this.car = response;
          console.log(this.car);
        },
        complete: () => {

        }, // completeHandler
      });
    }
    else {

      this.carId = "";

    }

  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }



  validateForm() {

    const invalidName = this.car.name == null || this.car.name == "";

    if (invalidName) {
      this.isNameValid = false;
    }
    else {
      this.isNameValid = true;
    }


    const invalidManufacturer = this.car.manufacturer == null || this.car.manufacturer == "";

    if (invalidManufacturer) {
      this.isManufacturerValid = false;
    }
    else {
      this.isManufacturerValid = true;
    }


    const invalidModel = this.car.model == null || this.car.model == "";

    if (invalidModel) {
      this.isModelValid = false;
    }
    else {
      this.isModelValid = true;
    }



    const invalidYear = this.car.year == null || this.car.year == "";

    if (invalidYear) {
      this.isYearValid = false;
    }
    else {
      this.isYearValid = true;
    }


    const invalidPlateNumber = this.car.plateNumber == null || this.car.plateNumber == "";

    if (invalidPlateNumber) {
      this.isPlateNumberValid = false;
    }
    else {
      this.isPlateNumberValid = true;
    }
  }

  isFormValid(): boolean {
    return this.isNameValid && this.isManufacturerValid && this.isModelValid && this.isYearValid && this.isPlateNumberValid
  }

  submit() {

    this.validateForm();

    if (!this.isFormValid()) {
      this.error = "Invalid Form"
      return;
    }


    if (this.car.userId == null || this.car.userId == 0 || this.car.userId != undefined) {

      const userId = this.jwtService.getUserId();

      if (userId == null) {
        this.error = "No valid user";
        return;
      }

      this.car.userId = userId;

    }
    if (this.carId == null || this.carId == "" || this.carId == undefined) {

      this.carService.createCar(this.car).subscribe({

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

          this.router.navigate([`/cars/user`]);
        },
        complete: () => {

        }, // completeHandler
      });
      // post
    } else {

      this.carService.updateCar(this.car).subscribe({

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

          this.router.navigate([`/cars/user`]);
        },
        complete: () => {

        }, // completeHandler
      });





      // put
    }



  }



}
