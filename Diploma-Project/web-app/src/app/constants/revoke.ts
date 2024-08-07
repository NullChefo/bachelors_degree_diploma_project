import { REDIRECT_URI, SCOPES, CLIENT_ID, AUTHORIZATION_URL, REVOKE_ENDPOINT, CLIENT_SECRET } from "src/environments/environment";

const revokeToken = (token: string) => {
    return `${REVOKE_ENDPOINT}?client_id=${CLIENT_ID}&client_secret=${CLIENT_SECRET}&token=${token}`;
}
export default revokeToken;
