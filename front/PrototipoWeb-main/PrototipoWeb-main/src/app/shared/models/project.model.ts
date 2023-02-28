import { Configuration } from "./configuration.model";
import { Mapping } from "./mapping.model";
import { Routine } from "./routine.model";

export class Project {
    constructor(
        public id?: number,
        public line?: number,
        public nome?: string,
        public creator?: string,
        public creationDate?: Date,
        public lastModificationDate?: Date,
        public mappings?: Mapping[],
        public routines?: Routine[],
        public configurations?: Configuration[],
        //public mappings?: string[],
        //public routines?: string[],
        //public configurations?: string[],
        public node?: number,
        public connections?: number[],
        public keyWords?: string[],
        public description?: string,
        public enabled?: boolean
    ) {}
} 