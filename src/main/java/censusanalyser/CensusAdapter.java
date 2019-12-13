package censusanalyser;

import com.csvReader.CSVBuilderException;
import com.csvReader.CSVBuilderFactory;
import com.csvReader.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public abstract class CensusAdapter {

    protected abstract <E> Map<String,CensusDAO> loadCountryData(String... csvFile) throws CensusAnalyserException;

    public <E> Map<String,CensusDAO> loadCensusData(Class<E> censusCsvClass,String... csvFile) throws CensusAnalyserException {
        try {
            Map<String,CensusDAO> censusStateMap=new HashMap<>();
            Reader reader = Files.newBufferedReader(Paths.get(csvFile[0]));
            ICSVBuilder icsvBuilder= CSVBuilderFactory.createCSVBuilder();
            Iterator<E> csvFileIterator = icsvBuilder.getCSVFileIterator(reader,censusCsvClass);
            Iterable<E> csvIterable = () -> csvFileIterator;
            if (censusCsvClass.getName().equals("censusanalyser.IndiaCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false).
                        map(IndiaCensusCSV.class::cast).
                        forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            } else if (censusCsvClass.getName().equals("censusanalyser.USCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false).
                        map(USCensusCSV.class::cast).
                        forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            return censusStateMap;
        } catch (IOException e) {
            throw new CensusAnalyserException("Incorrect File Input",
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        } catch (RuntimeException e) {
            throw new CensusAnalyserException("Incorrect Header or Delimiter",
                    CensusAnalyserException.ExceptionType.HEADER_INCORRECT_OR_INCORRECT_DELIMITER);
        }
    }

}
