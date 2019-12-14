package censusanalyser;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Sorting {
    public enum SortField{ STATE,AREA,DENSITY,POPULATION
    }

   static Map<SortField,Comparator> compareField= new HashMap<>();

    public Comparator getField(Sorting.SortField... sortField) {
        Comparator<CensusDAO> stateComparator= Comparator.comparing(census -> census.state);
        Comparator<CensusDAO> populationComparator= Comparator.comparing(census -> census.population);
        Comparator<CensusDAO> areaComparator= Comparator.comparing(census -> census.areaInSqKm);
        Comparator<CensusDAO> densityComparator= Comparator.comparing(census -> census.densityPerSqKm);
        compareField.put(SortField.STATE,stateComparator);
        compareField.put(SortField.AREA,areaComparator);
        compareField.put(SortField.DENSITY,densityComparator);
        compareField.put(SortField.POPULATION,populationComparator);
        Comparator<CensusDAO> comparator=compareField.get(sortField[0]);
        return comparator;
    }
}
