package censusanalyser;

import java.util.Map;

public class CensusAdapterFactory {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String US_CENSUS_DATA="/home/admin293/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/USCensusData.csv";

    public Object getCensusData(CensusAnalyser.Country country) throws CensusAnalyserException {
        if(country.equals(CensusAnalyser.Country.INDIA)) {
            return new IndiaCensusAdapter();
        }
        else
            return new USCensusAdapter();
        }

}
