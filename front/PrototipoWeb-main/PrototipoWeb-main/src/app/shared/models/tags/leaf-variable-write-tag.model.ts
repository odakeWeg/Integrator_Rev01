import { BaseTag } from "./base-tag.model";

export class LeafVariableWriteTag extends BaseTag {
    constructor(
        public ensSetup?: number
    ) {
        super()
    }
} 