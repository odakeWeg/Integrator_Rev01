import { BaseTag } from "./base-tag.model";

export class LeafEnsSetupTag extends BaseTag {
    constructor(
        
        public durationTimeUnitDielectric?: number,
        public timeUnitFunctional?: number,
        public timeUnitLoad?: number,
        public durationTimeUnitReneable?: number,

        public start?: boolean,
        public selectedItem?: string,

        public voltageSetpointDielectric?: number,
        public currentSetpointIn_mADielectric?: number,
        public currentAcceptanceIn_mADielectric?: number,
        public testDurationDielectric?: number,

        public voltageSetpointLoad?: number,
        public temperatureSetpointLoad?: number,
        public deratingPercentageLoad?: number,
        public speedSetpointInHzLoad?: number,
        public speedSetpointInRpmLoad?: number,
        public currentSetpointLoad?: number,
        public startDeratingTemperatureSetpointLoad?: number,
        
        public unitReneable?: number,
        public voltageMainReneable?: number,
        public networkFrequencyReneable?: number,
        public rectifierVoltageReneable?: number,
        public rectifierFrequencyReneable?: number,
        public coolantTemperatureReneable?: number,
        public coolantSystemPressureReneable?: number
    ) {
        super()
    }
} 