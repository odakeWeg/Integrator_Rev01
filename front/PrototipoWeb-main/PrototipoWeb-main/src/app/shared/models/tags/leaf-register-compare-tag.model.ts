import { BaseTag } from "./base-tag.model";

export class LeafRegisterCompareTag extends BaseTag {
    constructor(
        public communicationNameRef?: string,
        public registerNameRef?: string,
        public communicationNameOnTest?: string,
        public registerNameOnTest?: string,
        public calculateBy?: string,
        public registerRef?: number,
        //public valueRef?: number,
        public registerOnTest?: number,
        //public valueOnTest?: number,
        public tolerancy?: number,
        public waitBefore?: number,
        public waitAfter?: number
    ) {
        super()
    }
} 