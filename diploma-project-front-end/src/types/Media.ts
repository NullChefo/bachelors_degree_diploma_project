import {UserRetrieveDTO} from "./User";

export interface MediaRetrieveDTO {
  id?: number;
  path?: string;
  creator?: UserRetrieveDTO;
}

export interface MediaCreateDTO {
  postUUID?: string;
}
