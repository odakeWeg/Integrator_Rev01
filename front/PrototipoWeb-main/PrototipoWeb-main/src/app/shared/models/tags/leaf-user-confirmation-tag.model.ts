import { BaseTag } from "./base-tag.model";

export class LeafUserConfirmationTag extends BaseTag {
    constructor(
        //public confirmationValue?: boolean,
        public messageToDisplay?: string
    ) {
        super()
    }
} 