import {UserRetrieveDTO} from "./User";

export interface MessageCreateDTO {
  content?: string;
  recipientId?: number;
}

export interface MessageRetrieveDTO {
  id?: number;
  content?: string;
  recipient?: UserRetrieveDTO;
  creationDate?: Date;
}
