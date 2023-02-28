import { BaseTag } from "./base-tag.model";

export class LeafModbusCommunicationTag extends BaseTag {
    constructor(
        public communicationName?: string,
        public portName?: string,
        public parity?: string,
        public baudRate?: number,
        public dataBits?: number,
        public stopBits?: number,
        public timeoutComm?: number,
        public address?: number,
        public trials?: number,

        public mainCommunication?: boolean,
        public registerToFlag?: number,
        public registerToSetTimeout?: number,
        public waitBetweenFeedback?: number
    ) {
        super()
    }
} 