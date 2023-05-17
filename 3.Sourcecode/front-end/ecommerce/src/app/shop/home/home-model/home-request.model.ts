import {PageRequest} from "../../../admin/common/page-request.model";

export interface ProductRequest {

  pageRequest: PageRequest;
  productName: string;
  shortDescription:string;
  cost: number;
  imgAvt: string;


}
