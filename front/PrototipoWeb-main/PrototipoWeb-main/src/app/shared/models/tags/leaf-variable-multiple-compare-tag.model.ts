import { BaseTag } from "./base-tag.model";

export class LeafVariableMultipleCompareTag extends BaseTag {
    constructor(
        public variableName?: string[],
        public communicationNameOnTest?: string,
        public calculateBy?: string,
        public registerNameOnTest?: string,
        //public variableValue?: number,
        public registerOnTest?: number,
        //public valueRef?: number,
        public tolerancy?: number[],
        public quantityOfRegisters?: number,
        public waitBefore?: number,
        public waitAfter?: number
    ) {
        super()
    }
} 