import { BaseTag } from "./base-tag.model";

export class LeafMultipleWriteTag extends BaseTag {
    constructor(
        public ensSetup?: number
    ) {
        super()
    }
} 