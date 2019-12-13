package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIAN_STATE_CODE = "/home/admin293/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_FILE_TYPE = "./src/test/resources/IndiaStateCensusData.pdf";
    private static final String EMPTY_FILE_TYPE="/home/admin293/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/EmptyFile.csv";
    private static final String US_CENSUS_DATA="/home/admin293/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/USCensusData.csv";
    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadCensus(CensusAnalyser.Country.INDIA);
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
            censusAnalyser.loadCensus(CensusAnalyser.Country.INDIA,WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            e.printStackTrace();
        }
    }

//    @Test
//    public void givenIndiaStateData_WithWrongFile_ShouldThrowException() {
//        try {
//            CensusAnalyser censusAnalyser = new CensusAnalyser();
//            ExpectedException exceptionRule = ExpectedException.none();
//            exceptionRule.expect(CensusAnalyserException.class);
//           censusAnalyser.loadIndiaCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,WRONG_CSV_FILE_PATH);
//        } catch (CensusAnalyserException e) {
//            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_INCORRECT_OR_INCORRECT_DELIMITER, e.type);
//        }
//    }

//    @Test
//    public void givenIndiaStateCode_ShouldReturnExactCount() {
//        CensusAnalyser censusAnalyser = new CensusAnalyser();
//        int noOfStateCode = 0;
//        try {
//            noOfStateCode = censusAnalyser.loadIndiaCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CODE);
//            Assert.assertEquals(29, noOfStateCode);
//        } catch (CensusAnalyserException e) {
//            e.printStackTrace();
//        }
//    }

//    @Test
//    public void givenBothTheCsvFile_WhenSortedOnState_ShouldReturnSortedResult() {
//        CensusAnalyser censusAnalyser = new CensusAnalyser();
//        String sortedCensusData = null;
//        try {
//            censusAnalyser.loadIndiaCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CODE);
//            sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
//            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
//            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
//        } catch (CensusAnalyserException e) {
//            e.printStackTrace();
//        }
//    }

//    @Test
//    public void givenIndianCensusFile_WhenSorted_ShouldReturnSortedResult() {
//        CensusAnalyser censusAnalyser = new CensusAnalyser();
//        String sortedCensusData = null;
//        try {
//            censusAnalyser.loadIndiaCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
//            sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
//            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
//            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
//        } catch (CensusAnalyserException e) {
//            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_INCORRECT_OR_INCORRECT_DELIMITER,e.type);
//        }
//
//    }

//    @Test
//    public void givenWhenCensusCSVFile_WhenCorrectButTypeIncorrect_ShouldThrowException() {
//        CensusAnalyser censusAnalyser = new CensusAnalyser();
//        try {
//            censusAnalyser.loadIndiaCensusData(CensusAnalyser.Country.INDIA,INDIAN_STATE_CODE);
//        } catch (CensusAnalyserException e) {
//            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_INCORRECT_OR_INCORRECT_DELIMITER, e.type);
//            e.printStackTrace();
//        }
//    }

//    @Test
//    public void givenStateCodeFile_WhenCorrectButTypeIncorrect_ShouldThrowException() {
//        CensusAnalyser censusAnalyser = new CensusAnalyser();
//        try {
//            censusAnalyser.loadIndiaCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,WRONG_FILE_TYPE);
//        } catch (CensusAnalyserException e) {
//            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_INCORRECT_OR_INCORRECT_DELIMITER, e.type);
//        }
//    }

//    @Test
//    public void givenStateCensusFile_WhenCorrectButIncorrectDelimiter_ShouldThrowException() {
//        CensusAnalyser censusAnalyser = new CensusAnalyser();
//        try {
//            censusLoader.loadStateCode(INDIA_CENSUS_CSV_FILE_PATH);
//        } catch (CensusAnalyserException e) {
//            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_INCORRECT_OR_INCORRECT_DELIMITER, e.type);
//        }
//    }
//
//    @Test
//    public void givenStateCodeFile_WhenCorrectButIncorrectDelimiter_ShouldThrowException() {
//        CensusAnalyser censusAnalyser = new CensusAnalyser();
//        try {
//            censusAnalyser.loadIndiaCensusData(loadStateCode(INDIAN_STATE_CODE);
//        } catch (CensusAnalyserException e) {
//            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_INCORRECT_OR_INCORRECT_DELIMITER, e.type);
//        }
//    }
//
//    @Test
//    public void givenIndianCensusData_WhenSortedOnPopulation_ShouldReturnMostPopulous() {
//        CensusAnalyser censusAnalyser = new CensusAnalyser();
//        String sortedCensusData = null;
//        try {
//            censusAnalyser.loadIndiaCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
//            sortedCensusData = censusAnalyser.getPopulationWiseSortedCensusData();
//            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
//            Assert.assertEquals("Uttar Pradesh", censusCSV[28].state);
//        } catch (CensusAnalyserException e) {
//            e.printStackTrace();
//        }
//    }

//    @Test
//    public void givenIndianCensusData_WhenSortedOnArea_ShouldReturnLargestArea() {
//        CensusAnalyser censusAnalyser = new CensusAnalyser();
//        String sortedCensusData = null;
//        try {
//            censusAnalyser.loadIndiaCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
//            sortedCensusData = censusAnalyser.getAreaWiseSortedCensusData();
//            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
//            Assert.assertEquals("Rajasthan", censusCSV[28].state);
//        } catch (CensusAnalyserException e) {
//            e.printStackTrace();
//        }
//    }

//    @Test
//    public void givenIndianCensusData_WhenSortedOnDensity_ShouldReturnHighestDensityState() {
//        CensusAnalyser censusAnalyser = new CensusAnalyser();
//        String sortedCensusData = null;
//        try {
//            censusAnalyser.loadIndiaCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
//            sortedCensusData = censusAnalyser.getDensityWiseSortedCensusData();
//            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
//            Assert.assertEquals("Bihar", censusCSV[28].state);
//        } catch (CensusAnalyserException e) {
//            e.printStackTrace();
//        }
//    }

//    @Test
//    public void givenEmptyFile_ShouldThrowException() {
//        CensusAnalyser censusAnalyser = new CensusAnalyser();
//        try {
//            censusAnalyser.loadIndiaCensusData(CensusAnalyser.Country.INDIA,EMPTY_FILE_TYPE);
//            censusAnalyser.getStateWiseSortedCensusData();
//        } catch (CensusAnalyserException e) {
//            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA,e.type);
//            e.printStackTrace();
//        }
//    }

    @Test
    public void givenIndianCensusCSVFile_FromAdapterShouldReturnRecords() {
        IndiaCensusAdapter indiaCensusAdapter=new IndiaCensusAdapter();
        Map<String,CensusDAO> noOfRecords=null;
        try {
            noOfRecords = indiaCensusAdapter.loadCountryData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CODE);
            Assert.assertEquals(29,noOfRecords.size());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCsvFile_FromAnalyser_ShouldReturnRecords() {
        CensusAnalyser censusAnalyser=new CensusAnalyser();
        try {
            int noOfRecords=censusAnalyser.loadCensus(CensusAnalyser.Country.INDIA);
            Assert.assertEquals(29,noOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }

    }
}
