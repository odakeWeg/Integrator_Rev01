import { BaseTag } from "./base-tag.model";

export class LeafIOLinkCommunicationTag extends BaseTag {
    constructor(
        public communicationName?: string,
        public ip?: string,
        public port?: number,
        public address?: number,
        public trials?: number,
        public timeBetweenCommand?: number,

        public mainCommunication?: boolean,
        public registerToFlag?: number,
        public registerToSetTimeout?: number,
        public waitBetweenFeedback?: number
    ) {
        super()
    }
} 