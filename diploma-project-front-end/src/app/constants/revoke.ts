import {CLIENT_ID, CLIENT_SECRET, REVOKE_ENDPOINT} from "src/environments/environment";

const revokeToken = (token: string) => {
  return `${REVOKE_ENDPOINT}?client_id=${CLIENT_ID}&client_secret=${CLIENT_SECRET}&token=${token}`;
}
export default revokeToken;
