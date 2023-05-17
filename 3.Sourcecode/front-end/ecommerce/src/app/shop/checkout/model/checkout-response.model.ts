import { CheckOutDTOModel } from "./checkout-dto.model";

export interface CheckOutResponseModel{
    products: CheckOutDTOModel[];
    totalMany: number;
}