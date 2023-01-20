import { Jersey } from "./jersey";


export interface User{
    id: number,
    username: string,
    cart: Jersey[],
    name: string,
}