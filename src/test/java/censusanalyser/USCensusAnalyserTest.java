package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

public class USCensusAnalyserTest {

    private static final String US_CENSUS_DATA="/home/bridgeit/Desktop/Janhavi/jan/CensusReport/src/test/resources/USCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/USCensusData.csv";
    private static final String WRONG_CSV_FILE_TYPE="./src/test/resources/USCensusData.pdf";

    @Test
    public void givenUSCsvFile_FromAnalyser_ShouldReturnRecords() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        int noOfRecords=0;
        try {
            noOfRecords =censusAnalyser.loadCensus(CensusAnalyser.Country.US,US_CENSUS_DATA);
            Assert.assertEquals(51,noOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCSVFile_FromAdapterShouldReturnRecords() {
        USCensusAdapter usCensusAdapter=new USCensusAdapter();
        Map<String,CensusDAO> noOfRecords=null;
        try {
            noOfRecords=usCensusAdapter.loadCountryData(US_CENSUS_DATA);
            Assert.assertEquals(51,noOfRecords.size());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCsvData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensus(CensusAnalyser.Country.US,WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenWhenUSCSVFile_WhenCorrectButTypeIncorrect_ShouldThrowException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensus(CensusAnalyser.Country.US,WRONG_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCsvFile_WhenSortedOnState_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
        String sortedCensusData = null;
        try {
            censusAnalyser.loadCensus(CensusAnalyser.Country.US,US_CENSUS_DATA);
            sortedCensusData = censusAnalyser.getSort(CensusAnalyser.SortField.STATE);
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Alabama", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCsvFile_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
        String sortedCensusData = null;
        try {
            censusAnalyser.loadCensus(CensusAnalyser.Country.US,US_CENSUS_DATA);
            sortedCensusData = censusAnalyser.getSort(CensusAnalyser.SortField.POPULATION);
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Alaska", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCsvFile_WhenSortedOnArea_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
        String sortedCensusData = null;
        try {
            censusAnalyser.loadCensus(CensusAnalyser.Country.US,US_CENSUS_DATA);
            sortedCensusData = censusAnalyser.getSort(CensusAnalyser.SortField.AREA);
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("District of Columbia", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCsvFile_WhenSortedOnDensity_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
        String sortedCensusData = null;
        try {
            censusAnalyser.loadCensus(CensusAnalyser.Country.US,US_CENSUS_DATA);
            sortedCensusData = censusAnalyser.getSort(CensusAnalyser.SortField.DENSITY);
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("North Carolina", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

}
