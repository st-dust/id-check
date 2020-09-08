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

    private boolean isFirst = true;

    public MainView(@Autowired GetInfoService service) {

        TextField textField = new TextField("ID ke kontrole");
        textField.addThemeName("bordered");

        addClassName("centered-content");

        Dialog dialog = new Dialog();
        dialog.add(new Text("Informace o zamestnanci"));

//        Tab tab1 = new Tab("Je zaměstncem");
//        Tab tab2 = new Tab("Jméno zaměstnance");
//        Tab tab3 = new Tab("Středisko");
//        Tabs tabs = new Tabs(tab1, tab2, tab3);
//
//        dialog.add(tabs);

        Accordion accordion = new Accordion();

//        accordion.add("Je zaměstnanec", new Span())
//                .addThemeVariants(DetailsVariant.FILLED);
//
//        accordion.add("Jméno zaměstnance", new Span())
//                .addThemeVariants(DetailsVariant.FILLED);
//
//        accordion.add("Středisko", new Span())
//                .addThemeVariants(DetailsVariant.FILLED);

        dialog.add(accordion);

        dialog.setWidth("700px");
        dialog.setHeight("450px");

        //String name = service.getNameAndSurname(checkIfIsNumeric(textField.getValue()) ? Integer.parseInt(textField.getValue()) : -1);

//        Button button = new Button("Ověřít ID",
//                (e -> Notification.show(service.getNameAndSurname(checkIfIsNumeric(textField.getValue()) ? Integer.parseInt(textField.getValue()) : -1))));
//        Button button = new Button("Ověřít ID",
//                (e -> { tabs.set(new TextArea(service.getNameAndSurname(checkIfIsNumeric(textField.getValue()) ? Integer.parseInt(textField.getValue()) : -1))),
//                        tabs.set(new TextArea(service.getNameAndSurname(checkIfIsNumeric(textField.getValue()) ? Integer.parseInt(textField.getValue()) : -1)));
//                }));

        Button button = new Button("Ověřit ID",
                (e -> {
                    if(!isFirst) {
                        accordion.remove(accordion);
                    }

                    if (service.isAnEmployee(Integer.parseInt(textField.getValue()))) {
//                        dialog.add(accordion);

                        accordion.add("Je zaměstnanec", new Span("ANO")).addThemeVariants(DetailsVariant.FILLED);
                        accordion.add("Jméno zaměstnance", new Span(service.getNameAndSurname(Integer.parseInt(textField.getValue())))).addThemeVariants(DetailsVariant.FILLED);
                        accordion.add("Středisko", new Span(service.getDepartment(Integer.parseInt(textField.getValue())))).addThemeVariants(DetailsVariant.FILLED);
                    } else {
                        accordion.add("Je zaměstnanec", new Span("NE")).addThemeVariants(DetailsVariant.FILLED);
                    }
                    isFirst = false;
                }));



        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.addClickShortcut(Key.ENTER);
        button.addClickListener(event -> dialog.open());

        add(textField, button);

    }
}
