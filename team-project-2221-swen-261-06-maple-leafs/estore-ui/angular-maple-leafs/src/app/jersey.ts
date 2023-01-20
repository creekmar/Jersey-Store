/**
 * Author: maple leafs team
 * Interface to represent a jersey object 
 */

export enum Size {
    SMALL, MEDIUM, LARGE, XL,
}

export interface Jersey {
    id: number,
    name: string,
    cost: number,
    size: Size,
    home: boolean,
    number: number,
    discount: number,
}
