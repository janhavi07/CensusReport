package censusanalyser;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class USCensusAnalyserTest {

    private static final String US_CENSUS_DATA="/home/admin293/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/USCensusData.csv";
    @Test
    public void givenUSCensusData_ShouldReturnCorrectRecords() {
        USCensusAdapter usCensusAdapter=new USCensusAdapter();
        Map noOfRecords=null;
        try {
            noOfRecords = usCensusAdapter.loadCountryData(CensusAnalyser.Country.US,US_CENSUS_DATA);
            Assert.assertEquals(51,noOfRecords.size());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCSVFile_FromAdapterShouldReturnRecords() {
        USCensusAdapter usCensusAdapter=new USCensusAdapter();
        Map<String,CensusDAO> noOfRecords=null;
        try {
            noOfRecords=usCensusAdapter.loadCountryData(CensusAnalyser.Country.US,US_CENSUS_DATA);
            Assert.assertEquals(51,noOfRecords.size());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCsvFile_FromAnalyser_ShouldReturnRecords() {
        CensusAnalyser censusAnalyser=new CensusAnalyser();
        try {
            int noOfRecords=censusAnalyser.loadCensus(CensusAnalyser.Country.US);
            Assert.assertEquals(51,noOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }

    }
}
