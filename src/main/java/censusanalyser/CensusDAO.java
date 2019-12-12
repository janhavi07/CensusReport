package censusanalyser;

public class CensusDAO {

    public String state;
    public String StateCode;
    public int population;
    public int densityPerSqKm;
    public int areaInSqKm;

    public CensusDAO(IndiaCensusCSV csvFileIterator) {
        state = csvFileIterator.state;
        areaInSqKm=csvFileIterator.areaInSqKm;
        densityPerSqKm=csvFileIterator.densityPerSqKm;
        population=csvFileIterator.population;
    }

    public CensusDAO(USCensusCSV censusIterator){

        state=censusIterator.state;
//        areaInSqKm= (int) censusIterator.Area;
//        densityPerSqKm= (int)censusIterator.populationDensity;
//        StateCode=censusIterator.StateId;


    }

}