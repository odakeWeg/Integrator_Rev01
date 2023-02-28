import { BaseTag } from "./base-tag.model";

export class LeafVariableCompareTag extends BaseTag {
    constructor(
        public ensSetup?: number
    ) {
        super()
    }
} 