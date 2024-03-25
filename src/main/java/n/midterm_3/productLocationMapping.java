package n.midterm_3;

public class productLocationMapping {
    private int mappingID;
    private int productID;
    private String productName;
    private int locationID;

    public productLocationMapping(int mappingID, int productID, String productName, int locationID) {
        this.mappingID = mappingID;
        this.productID = productID;
        this.productName = productName;
        this.locationID = locationID;
    }

    public int getMappingID() {
        return mappingID;
    }

    public void setMappingID(int mappingID) {
        this.mappingID = mappingID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }
}

