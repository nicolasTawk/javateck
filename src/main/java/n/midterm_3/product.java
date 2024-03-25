package n.midterm_3;

public class product {

    int ProductID, QuantityInStock;
    double Price;
    String Name, Description;

    public product(int ProductID, String Name, String Description, double Price, int QuantityInStock ) {
       this.ProductID = ProductID;
       this.Name = Name;
       this.Description = Description;
       this.Price = Price;
       this.QuantityInStock = QuantityInStock;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public int getQuantityInStock() {
        return QuantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        QuantityInStock = quantityInStock;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
