import { BaseTag } from "./base-tag.model";

export class One extends BaseTag {
    constructor(
        public oneVariable?: number,
        public waitBefore?: number,
        public waitAfter?: number,
        public register?: string,
        public registerName?: string
    ) {
        super()
    }
} 