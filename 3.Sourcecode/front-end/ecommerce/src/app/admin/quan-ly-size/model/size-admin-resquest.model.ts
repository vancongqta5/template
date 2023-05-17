import { FilterDate } from "../../common/filter-date.model";
import { PageRequest } from "../../common/page-request.model";

export interface SizeAdminRequest{
    filterDate: FilterDate;
    pageRequest: PageRequest;
    sizeName: string;
    sizeCode: string;
}