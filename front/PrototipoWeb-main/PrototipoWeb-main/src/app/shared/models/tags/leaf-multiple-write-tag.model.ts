import { BaseTag } from "./base-tag.model";

export class LeafMultipleWriteTag extends BaseTag {
    constructor(
        public communicationName?: string,
        public registerName?: string,
        public register?: number,
        //public value?: number[],
        public quantityOfRegisters?: number,
        public waitBefore?: number,
        public waitAfter?: number
    ) {
        super()
    }
} 