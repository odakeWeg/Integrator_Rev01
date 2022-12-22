package weg.net.tester.models.ens;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
//@Todo: Melhorar arquitetura de modelos e afins
//@Todo: Front end provavlemente vai escolher entre enviar ou não o min max value
public class EnsTagConfiguration {
    private boolean enabled;
    private List<String> ensType = new ArrayList<>();
    private List<String> ensVariableName = new ArrayList<>();
    private List<String> variableToReadFrom = new ArrayList<>();
    
    private List<String> minValue = new ArrayList<>();
    private List<String> maxValue = new ArrayList<>();  //@Todo: Take from tolerancy so its not discrepant

    public void setAcceptanceRange(int max, int min) {
        maxValue.add(String.valueOf(max));
        minValue.add(String.valueOf(min));
    }
}
