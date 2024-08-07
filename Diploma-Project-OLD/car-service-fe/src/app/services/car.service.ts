import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CAR_PATH_V1 } from 'src/environments/environment';
import { Car } from '../types/Car';
import { JwtService } from './jwt.service';

@Injectable({
  providedIn: 'root'
})
export class CarService {



  getCarsForUserId(userId: string): Observable<Car[]> {
		return this.http.get<Car[]>(CAR_PATH_V1 + "/user/" + userId );
  }


  constructor(private http: HttpClient, private jwtToken: JwtService) { }


  getCar(carId: string): Observable<Car> {
    return this.http.get<Car>(CAR_PATH_V1 + "/" + carId );
  }


  getAll(): Observable<Car[]> {
      return this.http.get<Car[]>(CAR_PATH_V1 + "/" );
   
	}

  getCarsForUser(userId: string): Observable<Car[]> {
		return this.http.get<Car[]>(CAR_PATH_V1 + "/user/" + userId );
	}

  // TODO remove Deprecated
  getCarsForCurrentUser(): Observable<Car[]> {
    const userId = this.jwtToken.getUserId()
		return this.http.get<Car[]>(CAR_PATH_V1 + "/user/" + userId );
	}

  createCar(car: Car) {
    return	this.http.post(CAR_PATH_V1 + "/", car); 
	}

  updateCar(car: Car) {
    return	this.http.put(CAR_PATH_V1 + "/", car); 
	}


  deleteCar(carId: number): Observable<Object> {
		const param = new HttpParams().append('carId', carId)
		return	this.http.delete(CAR_PATH_V1 + "/", { params: param}); 
}


}
