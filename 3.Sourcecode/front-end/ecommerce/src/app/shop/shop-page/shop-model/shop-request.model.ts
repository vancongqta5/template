import {PageRequest} from "../../../admin/common/page-request.model";
import {FilterDate} from "../../../admin/common/filter-date.model";

export interface ProductRequest {

  // filterDate: FilterDate;
  pageRequest: PageRequest;
  productName: string;
  cost: number;
  categoryId: number


}
