package censusanalyser;

import java.util.Map;

public class CensusAdapterFactory {

    public CensusAdapter getCensusAdapter(CensusAnalyser.Country country) throws CensusAnalyserException {
        if (country.equals(CensusAnalyser.Country.INDIA))
            return new IndiaCensusAdapter();
        else
            return new USCensusAdapter();
    }
}
