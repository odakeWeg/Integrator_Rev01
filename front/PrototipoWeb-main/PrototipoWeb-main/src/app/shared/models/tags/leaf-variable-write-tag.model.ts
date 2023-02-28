import { BaseTag } from "./base-tag.model";

export class LeafVariableWriteTag extends BaseTag {
    constructor(
        public variableName?: string,
        public communicationNameRef?: string,
        public registerName?: string,
        //public variableValue?: number,
        public registerRef?: number,
        public waitBefore?: number,
        public waitAfter?: number
    ) {
        super()
    }
} 