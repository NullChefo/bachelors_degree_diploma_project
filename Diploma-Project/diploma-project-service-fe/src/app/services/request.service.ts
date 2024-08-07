import { Injectable, EventEmitter } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NotificationService } from './notification.service';

@Injectable()
export class RequestService {
	/*
		
	expireEmitter = new EventEmitter;
	deniedRequests = [];
	userId?:String;
	
	constructor(private http: HttpClient,
				private notifService: NotificationService) { }
	
	setTokenData(tokenData: any): void {
		if (!tokenData || !tokenData.details) return;
		
		this.userId = tokenData.details.userId;
	}
	
	redoDenied(tokenData: any): void {
		if (this.userId && this.userId != tokenData.details.userId) {
			window.location.reload();
			return;
		}
		
		this.setTokenData(tokenData);
		
		this.deniedRequests
			.forEach(r => {
				r.cbk(this.doRequest.apply(this, r.args));
			});
		this.deniedRequests.length = 0;
	}
	
	
	doRequest(name: string, url: string, params?: any): Promise<any> {
		return new Promise((r,rj) => {
			(this.http as any)[name](url, params)
				.toPromise()
				.then((result: any) => {
					r(result);
				})
				.catch((e: any) => {
					if (e.status == 401) {
					//	this.deniedRequests.push({ cbk: r, args: [name, url, params] });
						this.expireEmitter.emit();
					} else {
						rj(e);
					}
				});
		});
	}

	get(url: string): Promise<any> {
		return this.doRequest('get', url);
    }
    
    post(url: string, params: any): Promise<any> {
        return this.doRequest('post', url, params);
	}
	
	put(url: string, params: any): Promise<any> {
		return this.doRequest('put', url, params);
	}

    delete(url: string): Promise<any> {
        return this.doRequest('delete', url);
    }


	*/
}
