import { RecordLable } from "./RecordLable";

export interface SingerAllFields{
    id: number;
    firstName: string;
    lastName: string;
    age:number;
    city: string;
    typeOfMusic: string;
    recLbl: RecordLable;
}