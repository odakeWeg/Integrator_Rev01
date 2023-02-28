import { BaseTag } from "./base-tag.model";

export class LeafStringWriteTag extends BaseTag {
    constructor(
        public communicationName?: string,
        public registerName?: string,
        public value?: string,
        public register?: number,
        public waitBefore?: number,
        public waitAfter?: number
    ) {
        super()
    }
} 