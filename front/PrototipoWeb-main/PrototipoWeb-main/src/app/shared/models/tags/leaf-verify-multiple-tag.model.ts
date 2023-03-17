import { BaseTag } from "./base-tag.model";

export class LeafVerifyMultipleTag extends BaseTag {
    constructor(
        public valueRef?: number[],
        public communicationNameOnTest?: string,
        public registerNameOnTest?: string,
        public calculateBy?: string,
        //public valueRef?: number,
        public registerOnTest?: number,
        //public valueOnTest?: number,
        public tolerancy?: number[],
        public quantityOfRegisters?: number,
        public waitBefore?: number,
        public waitAfter?: number,
    ) {
        super()
    }
} 