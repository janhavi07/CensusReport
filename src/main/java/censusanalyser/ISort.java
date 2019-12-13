package censusanalyser;

import java.util.Comparator;

public interface ISort {
    public Comparator getStateWiseSortedCensusData();
    public Comparator getPopulationWiseSortedCensusData();
    public Comparator getAreaWiseSortedCensusData();
    public Comparator getDensityWiseSortedCensusData();


}
