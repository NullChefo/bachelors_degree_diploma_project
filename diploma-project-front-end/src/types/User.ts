import {MediaRetrieveDTO} from "./Media";

export interface UserRetrieveDTO {
  id?: number;
  firstName?: string;
  lastName?: string;
  username?: string;
  avatarURL?: string;
  oauth?: boolean;
  verified?: boolean;
  // pronouns?: string;
}

export interface EditUserDTO {
  id?: number;
  firstName?: string;
  lastName?: string;
  readonly username?: string; // cannot be changed
  avatarURL?: string;
  // password?: string;
  // confirmPassword?: string;
  email?: string;
  oauth?: boolean;
  university?: string;
  work?: string;
  school?: string;
  phone?: string;
  about?: string;
  websiteURL?: string;
  linkedInURL?: string;
  verified?: boolean;
  verifiedAt?: Date;
  pronouns?: string;
}


export interface ProfileRetrieveDTO {
  id?: number;
  firstName?: string;
  lastName?: string;
  username?: string;
  avatarURL?: string;
  attachments?: MediaRetrieveDTO[];
  connectionsCount?: number;
  oauth?: boolean;
  university?: string;
  work?: string;
  school?: string;
  gender?: string;
  phone?: string;
  about?: string;
  websiteURL?: string;
  linkedInURL?: string;
  verified?: boolean;
  verifiedAt?: Date;
  pronouns?: string;

}
