package cz.zatisigroup;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PreserveOnRefresh;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreserveOnRefresh
@RestController
@Route("")
@CssImport("./styles/shared-styles.css")
//@CssImport("./styles/mytheme-dialog.css")
@CssImport(value = "./styles/mytheme-dialog.css", themeFor = "vaadin-dialog-overlay")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {

    private static boolean checkIfIsNumeric(String id) {
        if (id == null){
            return false;
        }
        try {
            double d = Integer.parseInt(id);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private boolean dialogHasToPopUp = false;

    public MainView(@Autowired GetInfoService service) {

        TextField textField = new TextField("ID ke kontrole");
        textField.addThemeName("bordered");

        textField.setPlaceholder("Sem vložte ID");
        textField.setAutoselect(true);
        textField.setClearButtonVisible(true);

        addClassName("centered-content");

        Tabs tabs = new Tabs();

        Tab isAnEmployeeTab = new Tab();
        Tab nameTab = new Tab();
        Tab departmentTab = new Tab();

        tabs.add(isAnEmployeeTab, nameTab, departmentTab);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);

        Dialog dialog = new Dialog();
        dialog.add(new Text(""));
        dialog.setWidth("50%");
        dialog.setHeight("30%");


        dialog.getElement().getThemeList().add("mytheme-dialog-overlay");
        dialog.add(tabs);

        Button button = new Button("Ověřit ID",
                (e -> {
                    if(!checkIfIsNumeric(textField.getValue())) {

                        textField.setInvalid(true);
                        textField.setErrorMessage("Zadaná hodnota není platná");

                        dialogHasToPopUp = false;
                    } else if (service.isAnEmployee(Integer.parseInt(textField.getValue()))) {
                        //isAnEmployeeTab.getElement().getStyle().set("color", "green");

                        isAnEmployeeTab.setLabel("Je zaměstnanec ZCG");
                        nameTab.setLabel(service.getNameAndSurname(Integer.parseInt(textField.getValue())));
                        departmentTab.setLabel(service.getDepartment(Integer.parseInt(textField.getValue())));
                        dialogHasToPopUp = true;
                    } else {

                        textField.setInvalid(true);
                        textField.setErrorMessage("Identifikáční číslo " + textField.getValue() + " není vázané na žadného zaměstnance ZCG");

                        dialogHasToPopUp = false;
                    }
                }));

        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.addClickShortcut(Key.ENTER);
        button.addClickListener(event -> {
            if (dialogHasToPopUp) {
                dialog.open();
            }
        });

        add(textField, button);

    }
}
