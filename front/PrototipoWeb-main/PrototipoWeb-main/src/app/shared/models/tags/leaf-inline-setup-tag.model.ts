import { BaseTag } from "./base-tag.model";

export class LeafInlineSetupTag extends BaseTag {
    constructor(
        public enableInline?: boolean
    ) {
        super()
    }
} 