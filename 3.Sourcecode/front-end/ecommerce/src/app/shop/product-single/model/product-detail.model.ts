export interface ProductDetail{
     id: number;
     productName: string;
     cost: number;
     shortDescription: string;
     description: string;
     categoryName: string;
     colorDTOList: {
        id: number,
        colorName: string
     }[];
     sizeDTOList: {
        id: number;
        sizeCode: string;
     }[];
     imageDTOList:{
      id: number;
      url: string;
     }[]
}