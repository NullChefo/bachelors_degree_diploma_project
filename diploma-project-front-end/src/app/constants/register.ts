import {AUTH_SERVER_URI} from "src/environments/environment";

const register = () => {
  return `${AUTH_SERVER_URI}/register`;
}
export default register;
