// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false
};
export const UI_PORT = 4023;
export const API_URL = 'http://localhost:8080/api';
export const AUTH_PATH_V1 = API_URL + '/auth/v1';
export const USER_PATH_V1 = API_URL + '/user/v1';
export const CAR_PATH_V1 = API_URL + '/car/car/v1';
export const CAR_EVENT_PATH_V1 =  API_URL + '/car/event/v1';


/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */

 import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
