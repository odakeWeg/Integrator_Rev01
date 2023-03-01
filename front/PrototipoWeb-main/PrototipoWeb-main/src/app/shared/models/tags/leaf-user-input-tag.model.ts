import { BaseTag } from "./base-tag.model";

export class LeafUserInputTag extends BaseTag {
    constructor(
        //public userInputFailure?: boolean,
        public messageToDisplay?: string,
        public communicationNameRef?: string,
        public calculateBy?: string,
        //public inputValue?: number,
        public registerName?: string,
        public registerRef?: number,
        //public valueRef?: number,
        public tolerancy?: number,
        public waitBefore?: number,
        public waitAfter?: number
    ) {
        super()
    }
}