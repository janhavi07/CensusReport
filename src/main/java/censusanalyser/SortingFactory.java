package censusanalyser;

import java.util.Comparator;

public class SortingFactory {

    public Comparator getField(CensusAnalyser.SortField sortField) {
        switch (sortField){
            case AREA:
                return new Sort().getAreaWiseSortedCensusData();
            case STATE:
                return new Sort().getStateWiseSortedCensusData();
            case DENSITY:
                return new Sort().getDensityWiseSortedCensusData();
            case POPULATION:
                return new Sort().getPopulationWiseSortedCensusData();
        }
        return null;
    }
}
