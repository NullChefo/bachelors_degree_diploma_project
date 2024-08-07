export const environment = {
  production: false,
  inDevelopment: true,
};

export const UI_PORT = 4020;
export const CLIENT_ID = 'angular-client';
export const CLIENT_SECRET = "secret";
// export const AUTH_SERVER_URI= "http://127.0.0.1:9000";
export const AUTH_SERVER_URI = "http://localhost:9000";
export const REDIRECT_URI = "http://127.0.0.1:4200/auth/authorized";
export const API_URI = "http://localhost:8000";
export const SCOPES = "openid";
export const AUTHORIZATION_URL = AUTH_SERVER_URI + "/oauth2/authorize";
export const TOKEN_ENDPOINT = AUTH_SERVER_URI + "/oauth2/token";
export const REVOKE_ENDPOINT = AUTH_SERVER_URI + "/oauth2/revoke";
export const INTROSPECT_TOKEN_ENDPOINT = AUTH_SERVER_URI + "/oauth2/introspect";
export const USER_PATH_V1 = API_URI + "/authorization/user/v1";

export const POST_ENDPOINT = API_URI + "/social-media/v1/post";
export const GROUP_ENDPOINT = API_URI + "/social-media/v1/group";
export const MESSAGE_ENDPOINT = API_URI + "/social-media/v1/message";
export const CONNECTION_ENDPOINT = API_URI + "/social-media/v1/connection";
export const MEDIA_ENDPOINT = API_URI + "/social-media/v1/media";
export const FEED_ENDPOINT = API_URI + "/social-media/v1/feed";
export const LIKE_ENDPOINT = API_URI + "/social-media/v1/like";

export const COMMENT_ENDPOINT = API_URI + "/social-media/v1/comment";
export const USER_PATH_V2 = API_URI + "/social-media/v2/user";
