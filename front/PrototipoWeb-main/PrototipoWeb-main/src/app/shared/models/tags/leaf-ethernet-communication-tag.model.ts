import { BaseTag } from "./base-tag.model";

export class LeafEthernetCommunicationTag extends BaseTag {
    constructor(
        public ensSetup?: number
    ) {
        super()
    }
} 