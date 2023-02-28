import { BaseTag } from "./base-tag.model";

export class LeafIOLinkCommunicationTag extends BaseTag {
    constructor(
        public ensSetup?: number
    ) {
        super()
    }
} 