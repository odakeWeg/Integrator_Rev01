import { BaseTag } from "./base-tag.model";

export class LeafTestTag extends BaseTag {
    constructor(
        public ensSetup?: number
    ) {
        super()
    }
} 