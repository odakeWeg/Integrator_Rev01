import { BaseTag } from "./base-tag.model";

export class LeafModbusCommunicationTag extends BaseTag {
    constructor(
        public ensSetup?: number
    ) {
        super()
    }
} 