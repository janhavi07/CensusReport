package censusanalyser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

public class USCensusAnalyserTest {

    private static final String US_CENSUS_DATA="./src/test/resources/USCensusData.csv";
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

}
