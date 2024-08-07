import {UserRetrieveDTO} from "./User";

export interface LikeRetrieveDTO {
  id?: number;
  user?: UserRetrieveDTO;
}

export interface LikeCommentDTO {
  commentId?: number;
}

export interface LikePostDTO {
  postId?: number;
}
