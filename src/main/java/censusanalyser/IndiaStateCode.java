package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCode {

    @CsvBindByName(column = "State",required = true)
    public String StateName;

    @CsvBindByName
    public String StateCode;



    public String getState() {
        return StateName;
    }

    public void setState(String state) {
        StateName = state;
    }

    public String getStateCode() {
        return StateCode;
    }

    public void setStateCode(String stateCode) {
        StateCode = stateCode;
    }
}
