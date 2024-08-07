import {AUTHORIZATION_URL, CLIENT_ID, REDIRECT_URI, SCOPES} from "src/environments/environment";


const state = Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);
const redirectUri = (code_challenge?: string) => {
  // TODO make it automatic
  code_challenge = "QYPAZ5NU8yvtlQ9erXrUYR-T5AGCjCF47vN-KsaI2A8";
  //&state=${state}
  const redirectUri = `${encodeURIComponent(REDIRECT_URI)}&code_challenge=${code_challenge}&code_challenge_method=S256`;
  return `${AUTHORIZATION_URL}?response_type=code&client_id=${CLIENT_ID}&scope=${SCOPES}&redirect_uri=${redirectUri}`;
}
export default redirectUri;
