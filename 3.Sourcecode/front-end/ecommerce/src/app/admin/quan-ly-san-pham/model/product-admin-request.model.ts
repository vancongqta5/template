import { FilterDate } from "../../common/filter-date.model";
import { PageRequest } from "../../common/page-request.model";

export interface ProductAdminReQuestModel{
    filterDate: FilterDate;
    pageRequest: PageRequest;
    productName: string;
    quantity: number;
    cost: number;
    categoryId: number
}