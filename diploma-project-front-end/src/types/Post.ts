import {MediaRetrieveDTO} from "./Media";
import {UserRetrieveDTO} from "./User";

export interface PageOLD<T> {
  content?: T[];
  totalPages?: number;
  totalElements?: number;
}


export interface PostCreateDTO {
  postUUID?: string;
  content?: string;
  groupId?: number;
}

export interface PostRetrieveDTO {
  id?: number;
  content?: string;
  createdAt?: Date;
  creator?: UserRetrieveDTO;
  // comments?: CommentRetrieveDTO[];
  // likes?: LikeRetrieveDTO[];
  liked?: boolean;
  commented?: boolean;
  attachments?: MediaRetrieveDTO[];
  likeCount?: number;
  commentCount?: number;
}



