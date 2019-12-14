package censusanalyser;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {
    private Object country;

    public enum Country { INDIA,US}

    Map<String,CensusDAO> censusStateMap;

    public CensusAnalyser(Object country) {
        this.country = country;
    }
    public CensusAnalyser(){
        this.censusStateMap =new HashMap<>();
    }

    public int loadCensus(CensusAnalyser.Country country, String... csvFile) throws CensusAnalyserException {
        CensusAdapter censusAdapter=new CensusAdapterFactory().getCensusAdapter(country);
        censusStateMap=censusAdapter.loadCountryData(csvFile);
        return censusStateMap.size();
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

    public String getSort(Sorting.SortField... sortField) {
        Comparator<CensusDAO> censusCSVComparator = null;
        if(sortField.length==2) {
            censusCSVComparator = new Sorting().getField(sortField[0]).thenComparing(new Sorting().getField(sortField[1]));
            return this.getSortedString(censusCSVComparator);
        }
        censusCSVComparator=new Sorting().getField(sortField[0]);
        return this.getSortedString(censusCSVComparator);
    }

    private String getSortedString(Comparator<CensusDAO> censusCSVComparator){
        ArrayList censusDTO=censusStateMap.values().stream()
                .sorted(censusCSVComparator)
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(Collectors.toCollection(ArrayList::new));
        String sortedStateCensusJson=new Gson().toJson(censusDTO);
        return sortedStateCensusJson;
    }

}
