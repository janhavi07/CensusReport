package censusanalyser;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {
    public enum Country { INDIA,US
}
    Map<String,CensusDAO> censusStateMap;

    public CensusAnalyser(){
        this.censusStateMap =new HashMap<>();
    }

    public int loadCensus(CensusAnalyser.Country country) throws CensusAnalyserException {
        censusStateMap=new CensusAdapterFactory().getCensusData(country);
        return censusStateMap.size();
    }

    private String convertMapToList(Comparator<CensusDAO> censusCSVComparator) {
        List<CensusDAO> censusDAOS=censusStateMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS,censusCSVComparator);
        String sortedStateCensusJson=new Gson().toJson(censusDAOS);
        return sortedStateCensusJson;
    }
    public String getStateWiseSortedCensusData() {
        this.toThrowNullException();
        Comparator<CensusDAO> censusCSVComparator= Comparator.comparing(census -> census.state);
        return this.convertMapToList(censusCSVComparator);
    }

    private void sort(List<CensusDAO> censusDAOS, Comparator<CensusDAO> censusCSVComparator) {
        for (int i = 0; i < censusDAOS.size(); i++){
            for (int j = 0; j < censusDAOS.size() - 1; j++) {
                CensusDAO census1 = censusDAOS.get(j);
                CensusDAO census2 = censusDAOS.get(j + 1);
                if (censusCSVComparator.compare(census1, census2) > 0) {
                    censusDAOS.set(j, census2);
                    censusDAOS.set(j + 1, census1);
                }
            }
        }
    }
    private void toThrowNullException(){
        if( censusStateMap ==null || censusStateMap.size()==0){
            try {
                throw new CensusAnalyserException("No census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
            } catch (CensusAnalyserException e) {
                e.printStackTrace();
            }
        }
    }

    public String getPopulationWiseSortedCensusData() {
        this.toThrowNullException();
        Comparator<CensusDAO> censusCSVComparator= Comparator.comparing(census -> census.population);
        return this.convertMapToList(censusCSVComparator);
    }

    public String getDensityWiseSortedCensusData() {
        this.toThrowNullException();
        Comparator<CensusDAO> censusCSVComparator= Comparator.comparing(census -> census.densityPerSqKm);
        return this.convertMapToList(censusCSVComparator);
    }

    public String getAreaWiseSortedCensusData() {
        this.toThrowNullException();
        Comparator<CensusDAO> censusCSVComparator= Comparator.comparing(census -> census.areaInSqKm);
        return this.convertMapToList(censusCSVComparator);
    }


}
