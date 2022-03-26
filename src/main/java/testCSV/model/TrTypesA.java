package testCSV.model;


public class TrTypesA {

    private String tr_type;
    private String tr_description;

    @Override
    public String toString()
    {
        return "TrTypes {tr_type=" + tr_type +
                ", tr_description=" + tr_description  +
                "}";
    }
    public String getTr_type()
    {
        return tr_type;
    }
    public void setTr_type(String tr_type)
    {
        this.tr_type = tr_type;
    }
    public String getTr_description()
    {
        return tr_description;
    }
    public void setTr_description(String tr_description)
    {
        this.tr_description = tr_description;
    }




}
