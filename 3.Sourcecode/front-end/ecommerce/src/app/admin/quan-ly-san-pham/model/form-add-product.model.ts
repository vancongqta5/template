export interface FormAddProductModel{
    id: number;
    productName: string;
    quantity: number;
    cost: number;
    shortDescription: string;
    description: string;
    urlImg: string[];
    colorId: number;
    sizeId: number;
    discountId: number;
    categoryId: number;
}