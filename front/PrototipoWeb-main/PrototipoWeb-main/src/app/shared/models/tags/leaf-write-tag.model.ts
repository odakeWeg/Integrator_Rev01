import { BaseTag } from "./base-tag.model";

export class LeafWriteTag extends BaseTag {
    constructor(
        public ensSetup?: number
    ) {
        super()
    }
} 