import {UserRetrieveDTO} from "./User";

export interface CommentRetrieveDTO {
  id?: number;
  content?: string;
  timestamp?: Date;
  creator?: UserRetrieveDTO;
  retrieve?: string;
  // likes?: LikeRetrieveDTO[];
  liked?: boolean;
  likeCount?: number;
}

export interface CommentCreateDTO {
  content?: string;
  postId?: number;
}
