export interface CategoryModel{
    forEach(arg0: (res: any) => void): unknown;
    id: number,
    categoryName: string,
    description: string,
    createdDate: number,
    updatedDate: number
}