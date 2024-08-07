import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CAR_EVENT_PATH_V1 } from 'src/environments/environment';
import { EventType } from '../types/Event';
import { JwtService } from './jwt.service';

@Injectable({
  providedIn: 'root'
})
export class EventService {


  constructor(private http: HttpClient, private jwtToken: JwtService) { }


  getEvent(eventId: string): Observable<EventType> {
    return this.http.get<EventType>(CAR_EVENT_PATH_V1 + "/" + eventId );
  }

  getAll(): Observable<EventType[]> {
		return this.http.get<EventType[]>(CAR_EVENT_PATH_V1 + "/" );
	}

  getEventsForCar(carId: string): Observable<EventType[]> {
		return this.http.get<EventType[]>(CAR_EVENT_PATH_V1 + "/car/" + carId ); // TODO implement be
	}

  createEvent(event: EventType) {
    return	this.http.post(CAR_EVENT_PATH_V1 + "/", event); 
	}

  updateEvent(event: EventType) {
    return	this.http.put(CAR_EVENT_PATH_V1 + "/", event); 
	}


  deleteEvent(eventId: number): Observable<Object> {
		const param = new HttpParams().append('eventId', eventId)
		return	this.http.delete(CAR_EVENT_PATH_V1 + "/", { params: param}); 
}




}
