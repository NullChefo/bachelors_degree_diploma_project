export interface UserDataResult {
  authorities?: string;
}


export interface User {
  id?: number;
  username?: string;
  email?: string;
  password?: string;
  firstName?: string;
  lastName?: string;
  pronouns?: string;

}

export interface UserResetPasswordDTO {
  email?: string;
}

export interface ResetPassword {
  password?: string;
  matchingPassword?: string;
}

export interface Tokens {
  accessToken?: string;
  refreshToken?: string;
}

export interface NewPasswordDTO {
  oldPassword?: string,
  newPassword?: string
}


export interface UserRegisterDTO {
  email?: string;
  password?: string;
  username?: string;
  matchingPassword?: string;
  firstName?: string;
  lastName?: string;
  pronouns?: string;

  signedForAnnouncements?: boolean;
  signedForPromotions?: boolean;
  signedForNotifications?: boolean;

}


export interface Field {
  name?: string;
  type?: string;
  label?: string;
  validators?: string[];
}

export interface TaskFilter {
  status?: string;
  username?: string;
  createdBy?: string;
  taskName?: string;
}

export interface ResponsePage {
  content: any[];
  first: boolean;
  last: boolean;
  number?: number;
  numberOfElements?: number;
  pageable?: Pageable;
  size?: number;
  sort?: Sort;
  totalElements?: number;
  totalPages?: number;
}

export interface Pageable {
  offset?: number;
  pageNumber?: number;
  pageSize?: number;
  paged?: boolean;
  sort?: Sort;
  unpaged?: boolean;
}

export interface Sort {
  sorted?: boolean;
  unsorted?: boolean;
}
