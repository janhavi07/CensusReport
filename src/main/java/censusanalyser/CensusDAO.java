package censusanalyser;

import java.util.Iterator;

public class IndiaCensusDAO {

    public String state;
    public String StateCode;
    public int population;
    public int densityPerSqKm;
    public int areaInSqKm;


    public IndiaCensusDAO(IndiaCensusCSV csvFileIterator) {
        state = csvFileIterator.state;
        areaInSqKm=csvFileIterator.areaInSqKm;
        densityPerSqKm=csvFileIterator.densityPerSqKm;
        population=csvFileIterator.population;
    }

}