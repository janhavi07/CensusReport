package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {

    @CsvBindByName(column = "State",required = true)
    public String state;

    @CsvBindByName(column = "StateId",required = true)
    public String StateId;

    @CsvBindByName(column = "Totalarea",required = true)
    public double Area;

    @CsvBindByName(column = "Population",required = true)
    public int Population;

    @CsvBindByName(column = "Housingunits",required = true)
    public int Housingunits;

    @CsvBindByName(column = "PopulationDensity",required = true)
    public double populationDensity;

    @CsvBindByName(column = "Waterarea",required = true)
    public double Waterarea;

    @CsvBindByName(column = "Landarea",required = true)
    public double Landarea;

    @CsvBindByName(column = "HousingDensity",required = true)
    public double HousingDensity;

    public USCensusCSV(String state, String stateCode, int population, int area) {
        this.state = state;
        StateId = stateCode;
        Area = area;
        Population = population;
        //Housingunits = housingunits;
      //  this.populationDensity = population;
        //Waterarea = waterarea;
       // Landarea = landarea;
       // HousingDensity = housingDensity;
    }

    public USCensusCSV() {
    }
}
