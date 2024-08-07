export const environment = {
  production: true,
  inDevelopment: true,

};
export const UI_PORT = 4020;
export const CLIENT_ID = 'angular-client';
export const CLIENT_SECRET = "secret";
export const AUTH_SERVER_URI = "https://auth.nullchefo.com";
export const REDIRECT_URI = "https://diploma-project-ui.nullchefo.com/auth/authorized";
export const API_URI = "https://backend.nullchefo.com";
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
export const USER_PATH_V2 = API_URI + "/social-media/v2/user";

export const COMMENT_ENDPOINT = API_URI + "/social-media/v1/comment";
