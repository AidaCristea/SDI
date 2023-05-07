import { Group } from "./Group";
import { Singer } from "./Singer";
import { SingerAllFields } from "./SingerAllFields";

export interface AlbumAllFields{
    idAlbum:number;
    albumName: string;
    yearRelease: number;
    noSongs: number;
    group: Group;
    singer: SingerAllFields;
    // idGroup: number;
    // idSinger:number;
}