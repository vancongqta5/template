import { FilterDate } from "../../common/filter-date.model";
import { PageRequest } from "../../common/page-request.model";

export interface ColorRequestModel{
    filterDate: FilterDate;
    pageRequest: PageRequest;
    colorName: string;
    colorCode: string;
}