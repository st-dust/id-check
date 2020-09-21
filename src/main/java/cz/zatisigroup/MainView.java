package cz.zatisigroup;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextAreaVariant;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.material.Material;
import cz.zatisigroup.model.User;
import cz.zatisigroup.utills.ConvertToNumeric;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.bind.annotation.RestController;


import static cz.zatisigroup.utills.ConvertToNumeric.checkIfIsNumeric;

@PreserveOnRefresh
@RestController
@Route("")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
//@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainView extends VerticalLayout {

    private String textFieldValue;
    private boolean isNonsense = false;

    public MainView(@Autowired GetInfoService service) {

        TextField textField = new TextField("ID ke kontrole");
        textField.addThemeName("bordered");
        textField.setAutofocus(true);

        textField.setPlaceholder("Sem vložte ID");
        textField.setAutoselect(true);
        textField.setClearButtonVisible(true);

        User user = new User();

        Grid<User> grid = new Grid<>();
        TextArea successMessage = new TextArea();
        successMessage.setReadOnly(true);
        successMessage.addClassName("success-message");
        grid.setItems(user);

        grid.addColumn(User::getId).setHeader("ID");
        grid.addColumn(User::getPersonalNumber).setHeader("Osobní číslo");
        grid.addColumn(User::getName).setHeader("Jméno");
        grid.addColumn(User::getSurname).setHeader("Příjmeni");
        grid.addColumn(User::getDepartment).setHeader("Středisko");
        grid.addColumn(User::getDepartmentID).setHeader("ID střediska");

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);

        //addClassName("centered-content");
        textField.addClassName("centered-content-button-textfield");
       // successMessage.addClassName("success-message");
        successMessage.addThemeVariants(TextAreaVariant.LUMO_ALIGN_CENTER);
        grid.addClassName("v-grid");
        addClassName("centered-content");

        Button button = new Button("Ověřit ID",
                (e -> {
                    remove(grid, successMessage);

                    textFieldValue = textField.getValue();

                    if(!checkIfIsNumeric(textFieldValue)) {
                        if(checkIfIsNumeric(ConvertToNumeric.translate(textFieldValue))) {
                            textFieldValue = ConvertToNumeric.translate(textField.getValue());
                        } else {
                            isNonsense = true;
                        }
                    }

                    if (!isNonsense && service.isAnEmployee(Integer.parseInt(textFieldValue))) {
                        int textFieldIntValue = Integer.parseInt(textFieldValue);

                        user.setId(textFieldIntValue);
                        user.setPersonalNumber(service.getPersonalNumber(textFieldIntValue));
                        user.setName(service.getNameById(textFieldIntValue));
                        user.setSurname(service.getSurnameById(textFieldIntValue));
                        user.setDepartment(service.getDepartment(textFieldIntValue));
                        user.setDepartmentID(service.getDepartmentID(textFieldIntValue));

                        //grid.setItems(user);

                        successMessage.setValue(user.getName() + " " + user.getSurname() + " je zaměstnan/a v ZCG");
                        //successMessage.addClassName("success-message");

                        textField.setValue("");
                        add(successMessage, grid);
                    } else {
                        //remove(successMessage, grid);
                        textField.setInvalid(true);
                        textField.setErrorMessage("Identifikátor nenalezen. Není nárok na slevu");
                        isNonsense = false;
                    }
                }));

        button.setClassName("centered-content-button-textfield");
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.addClickShortcut(Key.ENTER);

        add(textField, button);


    }
}
