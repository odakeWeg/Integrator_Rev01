import { EnsTagConfiguration } from './../ens-tag-configuration.model';
export class BaseTag {
    constructor(
        public id?: number,
        //public line?: number,   //@Todo: change
        public tagName?: string,
        public simpleDescription?: string,
        public description?: string,
        public position?: number,
        public enabled?: boolean,
        public timeout?: number,
        public ensTagConfiguration?: EnsTagConfiguration
    ) {}
} 