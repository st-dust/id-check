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

    private boolean previousCheckEndedWithUnemployedPerson = false;

    public MainView(@Autowired GetInfoService service) {

        TextField textField = new TextField("ID ke kontrole");
        textField.addThemeName("bordered");

        addClassName("centered-content");

        Dialog dialog = new Dialog();
        dialog.add(new Text(""));

        Tabs tabs = new Tabs();
        Tab isAnEmployeeTab = new Tab();
        Tab nameTab = new Tab();
        Tab departmentTab = new Tab();

        tabs.add(isAnEmployeeTab, nameTab, departmentTab);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);

        //dialog.add(tabs);

        Accordion accordion = new Accordion();

        dialog.add(accordion);

        dialog.setWidth("700px");
        dialog.setHeight("450px");

        Button button = new Button("Ověřit ID",
                (e -> {
                    if(!checkIfIsNumeric(textField.getValue())) {

                        dialog.removeAll();
                        dialog.add(new Text("Zadaná hodnota není platná"));

                    } else if (service.isAnEmployee(Integer.parseInt(textField.getValue()))) {

                        dialog.removeAll();
                        dialog.add(tabs);

                        if (previousCheckEndedWithUnemployedPerson) {
                            tabs.add(nameTab, departmentTab);
                        }

                        isAnEmployeeTab.getElement().getStyle().set("color", "green");

                        isAnEmployeeTab.setLabel("Je zaměstnanec ZCG");
                        nameTab.setLabel(service.getNameAndSurname(Integer.parseInt(textField.getValue())));
                        departmentTab.setLabel(service.getDepartment(Integer.parseInt(textField.getValue())));
                    } else {
                        dialog.removeAll();
                        dialog.add(tabs);

                        tabs.remove(nameTab, departmentTab);
                        isAnEmployeeTab.getElement().getStyle().set("color",
                                "red");
                        isAnEmployeeTab.setLabel("Není zaměstnancem ZCG");
                        previousCheckEndedWithUnemployedPerson = true;
                    }
                }));


        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.addClickShortcut(Key.ENTER);
        button.addClickListener(event -> dialog.open());

        add(textField, button);

    }
}
