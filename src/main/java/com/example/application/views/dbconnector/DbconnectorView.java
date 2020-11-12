package com.example.application.views.dbconnector;

import com.example.application.backend.dto.DbConnectionDTO;
import com.example.application.backend.service.DbConnectionService;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "connector", layout = MainView.class)
@PageTitle("db-connector")
@CssImport("./styles/views/dbconnector/dbconnector-view.css")
@RouteAlias(value = "", layout = MainView.class)
public class DbconnectorView extends Div {
    private DbConnectionService dbConnectionService;

    private TextField dbInstanceName = new TextField("Data base instance name");
    private TextField hostname = new TextField("Host");
    private IntegerField port = new IntegerField("Port");
    private TextField databaseName = new TextField("Database name");
    private TextField username = new TextField("Username");
    private PasswordField password = new PasswordField("Password");

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Create connection");

    private Binder<DbConnectionDTO> binder = new Binder(DbConnectionDTO.class);

    public DbconnectorView(DbConnectionService personService) {
        setId("dbconnector-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        binder.bindInstanceFields(this);
        clearForm();

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {
            personService.createConnection(binder.getBean());
            Notification.show("New connection created");
            clearForm();
        });
    }

    private void clearForm() {
        binder.setBean(new DbConnectionDTO());
    }

    private Component createTitle() {
        return new H3("Personal information");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        port.setErrorMessage("Please enter a valid port");
        formLayout.add(dbInstanceName, hostname, port, databaseName, username, password);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        return buttonLayout;
    }


}
