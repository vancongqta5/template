import { FilterDate } from "../../common/filter-date.model";
import { PageRequest } from "../../common/page-request.model";

export interface DiscountRequest{
    filterDate: FilterDate
    pageRequest: PageRequest
    discountName: string;
    discountPercent: number;
}