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

public class IndiaCensusAdapter extends CensusAdapter {

    @Override
    protected <E> Map<String, CensusDAO> loadCountryData(String... csvFile) throws CensusAnalyserException {

        Map<String,CensusDAO> censusStateMap=super.loadCensusData(IndiaCensusCSV.class, csvFile);
        this.loadStateCode(censusStateMap,csvFile[1]);
        return censusStateMap;
    }

    protected int loadStateCode(Map<String,CensusDAO> censusStateMap,String indianStateCode) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(indianStateCode));
            ICSVBuilder icsvBuilder=CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCode> stateCodeIterator=icsvBuilder.getCSVFileIterator(reader,IndiaStateCode.class);
            Iterable<IndiaStateCode> indiaStateCodeIterable=() -> stateCodeIterator;
            StreamSupport.stream(indiaStateCodeIterable.spliterator(),false)
                    .filter(csvState -> censusStateMap.get(csvState.stateName)!=null)
                    .forEach(csvState -> censusStateMap.get(csvState.stateName).StateCode=csvState.stateCode);
            return censusStateMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e){
            throw new CensusAnalyserException("Header Missing or Incorrect Delimiter",
                    CensusAnalyserException.ExceptionType.HEADER_INCORRECT_OR_INCORRECT_DELIMITER);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }
}
