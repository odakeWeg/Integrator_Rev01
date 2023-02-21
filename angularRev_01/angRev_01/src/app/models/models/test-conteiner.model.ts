import { TagContainer } from './tag-conteiner.model';

export class TestContainer {
    testResult!: string
    
    testName!: string
    //@Todo: Progress bar (will be necessary a meta test addition)
    tagContainer!: TagContainer[]

    //testVisibility!: boolean //Changed for a possible bootstrap dropdown
    //@Todo: This (testVisibility) is not currently sent by the backend, check which would be the best option
  }