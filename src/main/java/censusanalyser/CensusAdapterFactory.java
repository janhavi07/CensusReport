package censusanalyser;

import java.util.Map;

public class CensusAdapterFactory {
    private static final String US_CENSUS_DATA="/home/admin293/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/USCensusData.csv";
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIAN_STATE_CODE = "/home/admin293/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/IndiaStateCode.csv";

    public Map<String, CensusDAO> getCensusData(CensusAnalyser.Country country) throws CensusAnalyserException {
        if(country.equals(CensusAnalyser.Country.INDIA))
            return new IndiaCensusAdapter().loadCountryData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CODE);
        else
            return new USCensusAdapter().loadCountryData(CensusAnalyser.Country.US,US_CENSUS_DATA);
        }
}
