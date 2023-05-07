import { RecordLable } from "./RecordLable";

export interface Singer{
    id: number;
    firstName: string;
    lastName: string;
    age:number;
    city: string;
    typeOfMusic: string;
    recLblId: number;
}