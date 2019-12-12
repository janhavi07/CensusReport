package censusanalyser;

import com.csvReader.CSVBuilderException;
import com.csvReader.CSVBuilderFactory;
import com.csvReader.ICSVBuilder;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    Map<String,CensusDAO> censusStateMap =null;

    public CensusAnalyser(){
        this.censusStateMap =new HashMap<>();
    }
    public int loadIndiaCensusData(String... csvFilePath) throws CensusAnalyserException {
        censusStateMap=new CensusLoader().loadCensusData(IndiaCensusCSV.class,csvFilePath[0]);
        return censusStateMap.size();
    }

    public int loadUSCensusData(String... usCensusData) throws CensusAnalyserException {
        censusStateMap=new CensusLoader().loadCensusData(USCensusCSV.class,usCensusData[0]);
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
