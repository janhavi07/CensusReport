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
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        return this.loadCensusData(csvFilePath,IndiaCensusCSV.class);
    }

    public int loadStateCode(String indianStateCode) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(indianStateCode));
            ICSVBuilder icsvBuilder=CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCode> stateCodeIterator=icsvBuilder.getCSVFileIterator(reader,IndiaStateCode.class);
            Iterable<IndiaStateCode> indiaStateCodeIterable=() -> stateCodeIterator;
            StreamSupport.stream(indiaStateCodeIterable.spliterator(),false)
                    .filter(csvState -> censusStateMap.get(csvState.StateName)!=null)
                    .forEach(csvState -> censusStateMap.get(csvState.StateName).StateCode=csvState.StateCode);
            return censusStateMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e){
            throw new CensusAnalyserException("Header Missing",
                    CensusAnalyserException.ExceptionType.HEADER_INCORRECT_OR_INCORRECT_DELIMITER);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }

    public int loadUSCensusData(String usCensusData) throws CensusAnalyserException {
        return this.loadCensusData(usCensusData,USCensusCSV.class);
    }

    private <E> int loadCensusData(String csvFile,Class<E> censusCsvClass) throws CensusAnalyserException {
        try {
             Reader reader = Files.newBufferedReader(Paths.get(csvFile));
             ICSVBuilder icsvBuilder= CSVBuilderFactory.createCSVBuilder();
             Iterator<E> csvFileIterator=icsvBuilder.getCSVFileIterator(reader,censusCsvClass);
             Iterable<E> csvIterable= () -> csvFileIterator;
             if (censusCsvClass.getName().equals("censusanalyser.IndiaCensusCSV")){
                        StreamSupport.stream(csvIterable.spliterator(), false).
                        map(IndiaCensusCSV.class::cast).
                        forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
             } else if(censusCsvClass.getName().equals("censusanalyser.USCensusCSV")) {
                         StreamSupport.stream(csvIterable.spliterator(), false).
                         map(USCensusCSV.class::cast).
                         forEach(censusCSV -> censusStateMap.put(censusCSV.State, new CensusDAO(censusCSV)));
             }
            return censusStateMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        } catch (RuntimeException e) {
            throw new CensusAnalyserException("Header missing", CensusAnalyserException.ExceptionType.HEADER_INCORRECT_OR_INCORRECT_DELIMITER);
        }
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
