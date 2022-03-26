package testCSV.model;

import java.io.Serializable;

public class ThMccCodeA implements Serializable {
    private static final long seriaVlersionUID = 1L;
    private String mcc_code;
    private String mcc_description;

    public String getMcc_code()
    {
        return mcc_code;
    }
    public void setMcc_code(String mcc_code)
    {
        this.mcc_code = mcc_code;
    }
    public String getMcc_description()
    {
        return mcc_description;
    }
    public void setMcc_description(String mcc_description)
    {
        this.mcc_description = mcc_description;
    }

    @Override
    public String toString()
    {
        return "ThMccCode [mcc_code=" + mcc_code + ", mcc_description=" + mcc_description  + "]";
    }

}
