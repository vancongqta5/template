import { FilterDate } from "../../common/filter-date.model";
import { PageRequest } from "../../common/page-request.model";

export interface CategoryRequestModel{
    pageRequest: PageRequest;
    filterDate: FilterDate;
    categoryName: string;
    description: string;
}