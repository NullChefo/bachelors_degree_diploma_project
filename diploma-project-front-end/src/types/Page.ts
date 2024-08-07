export interface Page<T> {
  content?: T[];
  pageable?: {
    pageNumber?: number;
    pageSize?: number;
    sort?: {
      sorted?: boolean;
      unsorted?: boolean;
      empty?: boolean;
    };
  };
  totalPages?: number;
  totalElements?: number;
  last?: boolean;
  first?: boolean;
  empty?: boolean;
}
