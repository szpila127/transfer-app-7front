package com.transfer.app7f.view.admin;

import com.transfer.app7f.domain.dto.UserDto;
import com.transfer.app7f.form.UserAdminForm;
import com.transfer.app7f.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route(value = "admin/users")
public class AdminUsersView extends VerticalLayout {

    private Button homeButton = new Button("HOME", VaadinIcon.HOME.create());
    private Button usersButton = new Button("USERS");
    private Button accountsButton = new Button("ACCOUNTS");
    private Button transactionsButton = new Button("TRANSACTIONS");
    private Button appEventsButton = new Button("APPLICATION EVENTS");
    private UserService userService = new UserService();
    private Grid<UserDto> gridUser = new Grid<>(UserDto.class);
    private TextField filterUsersByEmail = new TextField();
    private TextField filterUsersByPesel = new TextField();
    private UserAdminForm userAdminForm = new UserAdminForm(this);

    public AdminUsersView() {
        filterUsersByEmail.setPlaceholder("Filter by email...");
        filterUsersByEmail.setClearButtonVisible(true);
        filterUsersByEmail.setValueChangeMode(ValueChangeMode.EAGER);
        filterUsersByEmail.addValueChangeListener(e -> updateUserByEmail());

        filterUsersByPesel.setPlaceholder("Filter by pesel...");
        filterUsersByPesel.setClearButtonVisible(true);
        filterUsersByPesel.setValueChangeMode(ValueChangeMode.EAGER);
        filterUsersByPesel.addValueChangeListener(e -> updateUserByPesel());

        gridUser.setColumns("id", "email", "password", "pesel");
        gridUser.getColumnByKey("id").setHeader("ID");

        homeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        homeButton.addClickListener(event -> {
            homeButton.getUI().ifPresent(ui -> ui.navigate(""));
        });
        Button addNewUser = new Button("Add new user", VaadinIcon.PLUS_CIRCLE.create());
        addNewUser.addClickListener(event -> {
            gridUser.asSingleSelect().clear();
            userAdminForm.email.setEnabled(true);
            userAdminForm.pesel.setEnabled(true);
            userAdminForm.saveUserButton.setVisible(true);
            userAdminForm.deleteUserButton.setVisible(false);
            userAdminForm.updateUserButton.setVisible(false);
            userAdminForm.setUser(new UserDto());
        });

        HorizontalLayout filterFieldsUser = new HorizontalLayout(filterUsersByEmail, filterUsersByPesel, addNewUser);

        usersButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        accountsButton.addClickListener(event -> {
            homeButton.getUI().ifPresent(ui -> ui.navigate("admin/accounts"));
        });
        transactionsButton.addClickListener(event -> {
            homeButton.getUI().ifPresent(ui -> ui.navigate("admin/transactions"));
        });
        appEventsButton.addClickListener(event -> {
            homeButton.getUI().ifPresent(ui -> ui.navigate("admin/app-events"));
        });
        HorizontalLayout menuButtons = new HorizontalLayout(usersButton, accountsButton, transactionsButton, appEventsButton);

        HorizontalLayout mainContent = new HorizontalLayout(gridUser, userAdminForm);
        mainContent.setSizeFull();
        gridUser.setSizeFull();
        add(homeButton, menuButtons, filterFieldsUser, mainContent);
        userAdminForm.setUser(null);
        setSizeFull();
        refreshUsers();

        gridUser.asSingleSelect().addValueChangeListener(event -> {
            userAdminForm.email.setEnabled(false);
            userAdminForm.pesel.setEnabled(false);
            userAdminForm.saveUserButton.setVisible(false);
            userAdminForm.deleteUserButton.setVisible(true);
            userAdminForm.updateUserButton.setVisible(true);
            userAdminForm.setUser(gridUser.asSingleSelect().getValue());
        });
    }

    private void updateUserByEmail() {
        gridUser.setItems(userService.fillterByEmail(filterUsersByEmail.getValue()));
    }

    private void updateUserByPesel() {
        gridUser.setItems(userService.fillterByPesel(filterUsersByPesel.getValue()));
    }

    public void refreshUsers() {
        userService.fetchAll();
        gridUser.setItems(userService.getUserDtos());
    }
}
