import {CLIENT_ID, REDIRECT_URI, TOKEN_ENDPOINT} from "src/environments/environment";


const tokenUrl = (code: string, code_verifier?: string) => {
  // TODO make it automatic
  code_verifier = "qPsH306-ZDDaOE8DFzVn05TkN3ZZoVmI_6x4LsVglQI";
  const redirectUrl = `${encodeURIComponent(REDIRECT_URI)}&grant_type=authorization_code&code=${code}&code_verifier=${code_verifier}`;
  return `${TOKEN_ENDPOINT}?client_id=${CLIENT_ID}&redirect_uri=${redirectUrl}`;
};

export default tokenUrl;
