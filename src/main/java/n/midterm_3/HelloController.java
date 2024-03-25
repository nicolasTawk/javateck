package n.midterm_3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;



public class HelloController {
    @FXML
    private Label wrong;

    @FXML
    private Button login;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;



    @FXML
    public void userLogin(ActionEvent event) throws IOException {

        ConnectionManager c = new ConnectionManager();
      HelloApplication h = new HelloApplication();

        if (username.getText().equals("nicolas") && password.getText().equals("nicolas5t6u9")) {
            wrong.setText("Login successful");

            h.changeScene("list.fxml");
        } else if (username.getText().isEmpty() || password.getText().isEmpty()) {
            wrong.setText("Please enter data");
        } else {


            String s = String.valueOf(c.executeQuery("SELECT * FROM Product where ProductID = 1"));
            wrong.setText(s);
            System.out.println(s);

        }
    }




}
