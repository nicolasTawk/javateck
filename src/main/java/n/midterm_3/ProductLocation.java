package n.midterm_3;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;



    public class ProductLocation {
        private final SimpleIntegerProperty locationID;
        private final SimpleStringProperty aisle;
        private final SimpleStringProperty shelf;
        private final SimpleStringProperty bin;

        public ProductLocation(int locationID, String aisle, String shelf, String bin) {
            this.locationID = new SimpleIntegerProperty(locationID);
            this.aisle = new SimpleStringProperty(aisle);
            this.shelf = new SimpleStringProperty(shelf);
            this.bin = new SimpleStringProperty(bin);
        }

        public int getLocationID() {
            return locationID.get();
        }

        public SimpleIntegerProperty locationIDProperty() {
            return locationID;
        }

        public void setLocationID(int locationID) {
            this.locationID.set(locationID);
        }

        public String getAisle() {
            return aisle.get();
        }

        public SimpleStringProperty aisleProperty() {
            return aisle;
        }

        public void setAisle(String aisle) {
            this.aisle.set(aisle);
        }

        public String getShelf() {
            return shelf.get();
        }

        public SimpleStringProperty shelfProperty() {
            return shelf;
        }

        public void setShelf(String shelf) {
            this.shelf.set(shelf);
        }

        public String getBin() {
            return bin.get();
        }

        public SimpleStringProperty binProperty() {
            return bin;
        }

        public void setBin(String bin) {
            this.bin.set(bin);
        }
    }


