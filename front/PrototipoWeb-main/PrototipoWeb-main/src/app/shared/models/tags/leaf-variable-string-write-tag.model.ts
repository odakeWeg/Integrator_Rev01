import { BaseTag } from "./base-tag.model";

export class LeafVariableStringWriteTag extends BaseTag {
    constructor(
        public ensSetup?: number
    ) {
        super()
    }
} 