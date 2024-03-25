package n.midterm_3;

import java.time.LocalDate;

public class Batch {



        private int expirationID;
        private int productID;
        private LocalDate expirationDate;
        private int quantity;

        public Batch(int expirationID, int productID, LocalDate expirationDate, int quantity) {
            this.expirationID = expirationID;
            this.productID = productID;
            this.expirationDate = expirationDate;
            this.quantity = quantity;
        }

        // Getters and setters for each property
        // ...

        public int getExpirationID() {
            return expirationID;
        }

        public void setExpirationID(int expirationID) {
            this.expirationID = expirationID;
        }

        public int getProductID() {
            return productID;
        }

        public void setProductID(int productID) {
            this.productID = productID;
        }

        public LocalDate getExpirationDate() {
            return expirationDate;
        }

        public void setExpirationDate(LocalDate expirationDate) {
            this.expirationDate = expirationDate;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }


