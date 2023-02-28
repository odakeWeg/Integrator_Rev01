import { BaseTag } from "./base-tag.model";

export class LeafUserConfirmationTag extends BaseTag {
    constructor(
        public ensSetup?: number
    ) {
        super()
    }
} 