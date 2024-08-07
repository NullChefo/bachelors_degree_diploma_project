export const environment = {
     production: false,

};
export const UI_PORT = 4020;
export const CLIENT_ID = 'gateway';
export const CLIENT_SECRET = "secret";
// export const AUTH_SERVER_URI= "http://127.0.0.1:9000";
export const AUTH_SERVER_URI= "http://127.0.0.1:9000";
export const REDIRECT_URI= "http://127.0.0.1:4200/auth/authorized";
export const API_URI= "http://localhost:8080";
export const SCOPES= "openid"; //  user.read user.write offline_access
export const AUTHORIZATION_URL= AUTH_SERVER_URI + "/oauth2/authorize";
export const TOKEN_ENDPOINT = AUTH_SERVER_URI + "/oauth2/token";
export const REVOKE_ENDPOINT = AUTH_SERVER_URI + "/oauth2/revoke";
export const INTROSPECT_TOKEN_ENDPOINT = AUTH_SERVER_URI + "/oauth2/introspect";