import { BaseTag } from "./base-tag.model";

export class LeafRegisterCompareTag extends BaseTag {
    constructor(
        public ensSetup?: number
    ) {
        super()
    }
} 