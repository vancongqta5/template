import { FilterDate } from "../../common/filter-date.model";
import { PageRequest } from "../../common/page-request.model";

export interface AdminRevenueReq{
    filterDate: FilterDate;
    pageRequest: PageRequest;
    productName: string;
    idsCategory: number[]
}