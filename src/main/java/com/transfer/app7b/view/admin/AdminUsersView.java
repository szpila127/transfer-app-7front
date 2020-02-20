package com.transfer.app7b.view.admin;

import com.transfer.app7b.domain.dto.UserDto;
import com.transfer.app7b.form.UserAdminForm;
import com.transfer.app7b.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route(value = "admin/users")
public class AdminUsersView extends VerticalLayout {

    private Button homeButton = new Button("HOME");
    private Button usersButton = new Button("USERS");
    private Button accountsButton = new Button("ACCOUNTS");
    private Button transactionsButton = new Button("TRANSACTIONS");
    private Button appEventsButton = new Button("APPLICATION EVENTS");
    private UserService userService = UserService.getInstance();
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

        gridUser.setColumns("id", "email", "password", "pesel", "accounts");
        gridUser.getColumnByKey("id").setHeader("ID");

        homeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        homeButton.addClickListener(event -> {
            homeButton.getUI().ifPresent(ui -> ui.navigate("home"));
        });

    }

    private void updateUserByEmail() {
        gridUser.setItems(userService.fillterByEmail(filterUsersByEmail.getValue()));
    }

    private void updateUserByPesel() {
        gridUser.setItems(userService.fillterByPesel(filterUsersByPesel.getValue()));
    }

    public void refreshAccounts() {
        userService.fetchAll();
        gridUser.setItems(userService.getUserDtos());
    }
}
