import { BaseTag } from "./base-tag.model";

export class LeafVariableStringWriteTag extends BaseTag {
    constructor(
        public variableName?: string,
        //public variableValue?: string,
        public communicationNameRef?: string,
        public registerName?: string,
        public registerRef?: number,
        public waitBefore?: number,
        public waitAfter?: number
    ) {
        super()
    }
} 