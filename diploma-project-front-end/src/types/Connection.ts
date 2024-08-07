import {UserRetrieveDTO} from "./User";

export interface ConnectionRetrievalDTO {
  id?: number;
  connection?: UserRetrieveDTO;
  connectionDate?: Date;
  accepted?: boolean;

}
