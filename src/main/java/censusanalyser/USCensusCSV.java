package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {

    @CsvBindByName(column = "State",required = true)
    public String State;

    @CsvBindByName(column = "State Id",required = true)
    public int StateId;

    @CsvBindByName(column = "Total area",required = true)
    public double Area;

    @CsvBindByName(column = "Population Density",required = true)
    public double populationDensity;

    public String getUsState() {
        return State;
    }

    public void setUsState(String usState) {
        this.State = usState;
    }

    public int getUsStateId() {
        return StateId;
    }

    public void setUsStateId(int usStateId) {
        this.StateId = usStateId;
    }

    public double getUsArea() {
        return Area;
    }

    public void setUsArea(double usArea) {
        this.Area = usArea;
    }

    public double getPopulationDensity() {
        return populationDensity;
    }

    public void setPopulationDensity(int populationDensity) {
        this.populationDensity = populationDensity;
    }
}
