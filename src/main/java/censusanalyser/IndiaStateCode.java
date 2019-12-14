package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCode {

    @CsvBindByName(column = "StateName",required = true)
    public String stateName;

    @CsvBindByName(column = "StateCode",required = true)
    public String stateCode;

    public String getState() {
        return stateName;
    }

    public void setState(String state) {
        stateName = state;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }
}
