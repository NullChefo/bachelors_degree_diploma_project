import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { USER_PATH_V1 } from 'src/environments/environment';
import { NewPasswordDTO, ResetPassword, ResponsePage, TaskFilter, User, UserRegisterDTO, UserResetPasswordDTO } from '../types/User';

import { RequestService } from './request.service';


@Injectable({
	providedIn: 'root'
})
export class UserService {




	user?: User[];
	userLoaded = new EventEmitter;
	userLoading = new EventEmitter;
	userError = new EventEmitter;

	constructor(private http: HttpClient, private router: Router) {
	}

	editUser(user: User): Observable<any> {
		return this.http.put(USER_PATH_V1 + '/' + user.id, user);
	}

	deleteUser(id: string): Observable<any> {
		return this.http.delete(USER_PATH_V1 + '/' + id);
	}

	// Uses the request 
	importUser(user: User): Observable<any> {
		return this.http.post(USER_PATH_V1 + '/import', user);

	}
	getUserById(userId?: string): Observable<User> {
		return this.http.get<User>(USER_PATH_V1 + "/" + userId);
	}

	register(userRegisterDTO: UserRegisterDTO) {
		return this.http.post<UserRegisterDTO>(USER_PATH_V1 + "/register", userRegisterDTO);
	}


	redirectTo(address: string) {
		this.router.navigate([`${address}`]);
	}


	sendResetPasswordRequest(resetDTO: UserResetPasswordDTO): Observable<UserResetPasswordDTO> {
		return this.http.post<UserResetPasswordDTO>(USER_PATH_V1 + "/resetPassword", resetDTO);
	}

	validateRegistrationToken(token: string): Observable<Object>{
		const param = new HttpParams().append('token', token)
		return	this.http.get(USER_PATH_V1 + "/verifyRegistration", { params: param}); 
	  }


	getNewRegistrationToken(oldToken: string){
		const param = new HttpParams().append('token', oldToken)
		return	this.http.get(USER_PATH_V1 + "/resendVerifyToken", { params: param}); 

	}

	sendNewPasswordRequest(resetDTO: NewPasswordDTO, token: string) {
		const param = new HttpParams().append('token', token)
		return	this.http.post(USER_PATH_V1 + "/savePassword", resetDTO, { params: param}); 
		
	  }



}
