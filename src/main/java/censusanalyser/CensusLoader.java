package censusanalyser;

import com.csvReader.CSVBuilderException;
import com.csvReader.CSVBuilderFactory;
import com.csvReader.ICSVBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class CensusLoader {

    Map<String,CensusDAO> censusStateMap =null;

    public Map<String,CensusDAO> loadCensusData(CensusAnalyser.Country country,String... csvFile) throws CensusAnalyserException {
        if(country.equals(CensusAnalyser.Country.INDIA))
            return this.loadCensusData(IndiaCensusCSV.class,csvFile);
        else if (country.equals(CensusAnalyser.Country.US))
                return this.loadCensusData(USCensusCSV.class,csvFile);
        else throw new CensusAnalyserException("Incorrect Country",CensusAnalyserException.ExceptionType.INCORRECT_COUNTRY);
    }

    private  <E> Map<String,CensusDAO> loadCensusData(Class<E> censusCsvClass,String... csvFile) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFile[0]));
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
                        forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            if(csvFile.length==1) return censusStateMap;
            this.loadStateCode(censusStateMap,csvFile[1]);
            return censusStateMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        } catch (RuntimeException e) {
            throw new CensusAnalyserException("Header missing", CensusAnalyserException.ExceptionType.HEADER_INCORRECT_OR_INCORRECT_DELIMITER);
        }
    }

    private int loadStateCode(Map<String,CensusDAO> censusStateMap,String indianStateCode) throws CensusAnalyserException {
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
}
