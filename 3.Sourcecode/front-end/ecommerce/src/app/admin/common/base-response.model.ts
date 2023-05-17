export interface BaseReponse<T> {
    forEach(arg0: (res: any) => void): unknown;
    data: T;
    message: string;
    status: number;
    totalRecords: number;
}