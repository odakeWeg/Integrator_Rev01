import { BaseTag } from "./base-tag.model";

export class LeafUserInputTag extends BaseTag {
    constructor(
        public ensSetup?: number
    ) {
        super()
    }
} 