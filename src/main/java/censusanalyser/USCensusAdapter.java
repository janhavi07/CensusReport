package censusanalyser;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter {

    @Override
    protected <E> Map<String, CensusDAO> loadCountryData(String... csvFile)
                                                                                throws CensusAnalyserException {
        Map<String,CensusDAO> censusStateMap=super.loadCensusData(USCensusCSV.class,csvFile);
        return censusStateMap;
    }
}
