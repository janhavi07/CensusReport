package censusanalyser;

import java.util.Comparator;

public class Sort implements ISort {
    @Override
    public Comparator getStateWiseSortedCensusData() {
        Comparator<CensusDAO> censusCSVComparator= Comparator.comparing(census -> census.state);
        return censusCSVComparator;
    }

    @Override
    public Comparator getPopulationWiseSortedCensusData() {
        Comparator<CensusDAO> censusCSVComparator= Comparator.comparing(census -> census.population);
        return censusCSVComparator;
    }

    @Override
    public Comparator getAreaWiseSortedCensusData() {
        Comparator<CensusDAO> censusCSVComparator= Comparator.comparing(census -> census.areaInSqKm);
        return censusCSVComparator;
    }

    @Override
    public Comparator getDensityWiseSortedCensusData() {
        Comparator<CensusDAO> censusCSVComparator= Comparator.comparing(census -> census.densityPerSqKm);
        return censusCSVComparator;
    }
}
