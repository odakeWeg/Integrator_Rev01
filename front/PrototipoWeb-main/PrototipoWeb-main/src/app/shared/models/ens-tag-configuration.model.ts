export class EnsTagConfiguration {
    constructor(
        public enabled?: boolean,
        public ensType?: string[],
        public ensVariableName?: string[],
        public variableToReadFrom?: string[],
    ) {}
} 