package model;
/**
 OutSourced Part Class. This class will be used to create out sourced parts.
 @author Peter Chouinard.
 */
public class OutSourced extends Part
{
    private String companyName;

    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
/**
 This is a getter for our company name parameter.
 @return companyName.
 */
    public String getCompanyName() {
        return companyName;
    }
/**
 This is a setter for our company name parameter.
 @param companyName name of company.
 */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}