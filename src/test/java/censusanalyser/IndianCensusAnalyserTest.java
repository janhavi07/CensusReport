package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

public class IndianCensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIAN_STATE_CODE = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_FILE_TYPE = "./src/test/resources/IndiaStateCensusData.pdf";
    private static final String EMPTY_FILE_TYPE = "./src/test/resources/EmptyFile.csv";
    private static final String INCORRECT_DELIMITER = "./src/test/resources/IncorrectDeliimiterIndiaStateCensusData.csv";

    @Test
    public void givenIndiaCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadCensus(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIAN_STATE_CODE);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensus(CensusAnalyser.Country.INDIA, WRONG_CSV_FILE_PATH, INDIAN_STATE_CODE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaStateData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensus(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCsvFile_FromAdapterShouldReturnRecords() {
        IndiaCensusAdapter indiaCensusAdapter = new IndiaCensusAdapter();
        Map<String, CensusDAO> noOfRecords = null;
        try {
            noOfRecords = indiaCensusAdapter.loadCountryData(INDIA_CENSUS_CSV_FILE_PATH, INDIAN_STATE_CODE);
            Assert.assertEquals(29, noOfRecords.size());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenWhenCensusCSVFile_WhenCorrectButTypeIncorrect_ShouldThrowException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensus(CensusAnalyser.Country.INDIA, WRONG_FILE_TYPE, INDIAN_STATE_CODE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusFile_WhenCorrectButIncorrectDelimiter_ShouldThrowException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensus(CensusAnalyser.Country.INDIA, INCORRECT_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_INCORRECT_OR_INCORRECT_DELIMITER, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCodeFile_WhenCorrectButIncorrectDelimiter_ShouldThrowException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensus(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INCORRECT_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_INCORRECT_OR_INCORRECT_DELIMITER, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenEmptyFile_WithOnlyHeader_ShouldThrowException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensus(CensusAnalyser.Country.INDIA, EMPTY_FILE_TYPE, INDIAN_STATE_CODE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_INCORRECT_OR_INCORRECT_DELIMITER, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenBothTheCsvFile_WhenSortedOnState_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        String sortedCensusData = null;
        try {
            censusAnalyser.loadCensus(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIAN_STATE_CODE);
            sortedCensusData = censusAnalyser.getSort(Sorting.SortField.STATE);
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulation_ShouldReturnMostPopulous() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        String sortedCensusData = null;
        try {
            censusAnalyser.loadCensus(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIAN_STATE_CODE);
            sortedCensusData = censusAnalyser.getSort(Sorting.SortField.POPULATION);
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Uttar Pradesh", censusCSV[28].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnArea_ShouldReturnLargestArea() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        String sortedCensusData = null;
        try {
            censusAnalyser.loadCensus(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIAN_STATE_CODE);
            sortedCensusData = censusAnalyser.getSort(Sorting.SortField.AREA);
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Rajasthan", censusCSV[28].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnDensity_ShouldReturnHighestDensityState() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        String sortedCensusData = null;
        try {
            censusAnalyser.loadCensus(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIAN_STATE_CODE);
            sortedCensusData = censusAnalyser.getSort(Sorting.SortField.POPULATION);
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Uttar Pradesh", censusCSV[28].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenSamePopulationCensusData_WhenSorted_ShouldReturnHighestDensityState_IfPopulationIsSame() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        String sortedCensusData = null;
        try {
            censusAnalyser.loadCensus(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH, INDIAN_STATE_CODE);
            sortedCensusData = censusAnalyser.getSort(Sorting.SortField.POPULATION, Sorting.SortField.DENSITY);
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Maharashtra", censusCSV[28].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
}
