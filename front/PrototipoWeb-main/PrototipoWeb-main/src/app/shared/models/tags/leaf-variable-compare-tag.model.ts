import { BaseTag } from "./base-tag.model";

export class LeafVariableCompareTag extends BaseTag {
    constructor(
        public variableName?: string,
        public communicationNameRef?: string,
        public calculateBy?: string,
        public registerName?: string,
        //public variableValue?: number,
        public registerRef?: number,
        //public valueRef?: number,
        public tolerancy?: number,
        public waitBefore?: number,
        public waitAfter?: number
    ) {
        super()
    }
} 