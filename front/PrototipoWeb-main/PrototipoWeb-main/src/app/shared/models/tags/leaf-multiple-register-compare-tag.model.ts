import { BaseTag } from "./base-tag.model";

export class LeafMultipleRegisterCompareTag extends BaseTag {
    constructor(
        public communicationNameRef?: string,
        public registerNameRef?: string,
        public communicationNameOnTest?: string,
        public registerNameOnTest?: string,
        public calculateBy?: string,
        public registerRef?: number,
        public registerOnTest?: number,
        public quantityOfRegisters?: number,
        public waitBefore?: number,
        public waitAfter?: number,
        //public valueRef?: number[],
        //public valueOnTest?: number[],
        public tolerancy?: number[]
    ) {
        super()
    }
} 