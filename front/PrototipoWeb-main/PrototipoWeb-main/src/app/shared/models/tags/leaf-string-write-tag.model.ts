import { BaseTag } from "./base-tag.model";

export class LeafStringWriteTag extends BaseTag {
    constructor(
        public ensSetup?: number
    ) {
        super()
    }
} 