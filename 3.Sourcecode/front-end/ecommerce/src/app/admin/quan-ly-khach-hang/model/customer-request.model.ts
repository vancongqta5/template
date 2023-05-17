import { PageRequest } from "../../common/page-request.model";

export interface CustomerRequest{
    pageRequest: PageRequest;
    textSearch: string;
    phoneNumber: string;
    status: number;
}