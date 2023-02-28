import { BaseTag } from "./base-tag.model";

export class LeafWriteTag extends BaseTag {
    constructor(
        public register?: number,
        //public value?: number,
        public communicationName?: string,
        public registerName?: string,
        public waitBefore?: number,
        public waitAfter?: number
    ) {
        super()
    }
} 