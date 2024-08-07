import {MediaRetrieveDTO} from "./Media";
import {UserRetrieveDTO} from "./User";

export interface GroupCreateDTO {
  name?: string;
  description?: string;

}

export interface GroupRetrieveDTO {
  id?: string;
  name?: string;
  description?: string;
  createdAt?: Date;
  updatedAt?: Date;
  owner?: UserRetrieveDTO;
  members?: UserRetrieveDTO[];
  avatarURL?: string;
  attachments?: MediaRetrieveDTO[];
  memberCount?: number;
}
