package censusanalyser;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusDAO> censusList=null;

    public CensusAnalyser(){

        this.censusList=new ArrayList<IndiaCensusDAO>();
    }
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder icsvBuilder=CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvFileIterator=icsvBuilder.getCSVFileIterator(reader,IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> csvIterable= () -> csvFileIterator;
            StreamSupport.stream(csvIterable.spliterator(),false).
                    forEach(indiaCensusCSV -> censusList.add(new IndiaCensusDAO(indiaCensusCSV)));
            return censusList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }catch (NullPointerException e) {
            throw new CensusAnalyserException("Header missing", CensusAnalyserException.ExceptionType.HEADER_INCORRECT_OR_INCORRECT_DELIMITER);
        }
    }

    public int loadStateCode(String indianStateCode) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(indianStateCode));
            ICSVBuilder icsvBuilder=CSVBuilderFactory.createCSVBuilder();
            return icsvBuilder.getCSVFileList(reader,IndiaStateCode.class).size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e){
            throw new CensusAnalyserException("Header Missing",
                    CensusAnalyserException.ExceptionType.HEADER_INCORRECT_OR_INCORRECT_DELIMITER);
        }catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }


    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        toThrowNullPointerException();
        Comparator<IndiaCensusDAO> censusCSVComparator= Comparator.comparing(census -> census.state);
        this.sort(censusCSVComparator);
        String sortedStateCensusJson=new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }


    private void sort(Comparator<IndiaCensusDAO> censusCSVComparator) {
        for (int i = 0; i < censusList.size(); i++){
            for (int j = 0; j < censusList.size() - 1; j++) {
                IndiaCensusDAO census1 = censusList.get(j);
                IndiaCensusDAO census2 = censusList.get(j + 1);
                if (censusCSVComparator.compare(census1, census2) > 0) {
                    censusList.set(j, census2);
                    censusList.set(j + 1, census1);
                }
            }
        }
    }

    public String getPopulationWiseSortedCensusData() {
        toThrowNullPointerException();
        Comparator<IndiaCensusDAO> censusCSVComparator= Comparator.comparing(census -> census.population);
        this.sort(censusCSVComparator);
        String sortedStateCensusJson=new Gson().toJson(censusList);
        return sortedStateCensusJson;

    }

    private void toThrowNullPointerException(){
        if( censusList==null ||censusList.size()==0){
            try {
                throw new CensusAnalyserException("No census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
            } catch (CensusAnalyserException e) {
                e.printStackTrace();
            }
        }
    }

    public String getAreaWiseSortedCensusData() {
        toThrowNullPointerException();
        Comparator<IndiaCensusDAO> censusCSVComparator= Comparator.comparing(census -> census.areaInSqKm);
        this.sort(censusCSVComparator);
        String sortedStateCensusJson=new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }
}
