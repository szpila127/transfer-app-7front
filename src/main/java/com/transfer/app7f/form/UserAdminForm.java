package com.transfer.app7f.form;

import com.transfer.app7f.domain.dto.UserDto;
import com.transfer.app7f.service.UserService;
import com.transfer.app7f.view.admin.AdminUsersView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;

public class UserAdminForm extends FormLayout {

    public TextField email = new TextField("Email");
    private TextField password = new TextField("Password");
    public TextField pesel = new TextField("Pesel");
    public Button saveUserButton = new Button("Save");
    public Button updateUserButton = new Button("Update");
    public Button deleteUserButton = new Button("Delete");
    public Button cancelUserButton = new Button("Cancel");
    Notification notification = new Notification();
    private Binder<UserDto> binder = new Binder<>(UserDto.class);
    private UserService userService = new UserService();

    public AdminUsersView adminUsersView;

    public UserAdminForm(AdminUsersView adminUsersView2) {
        HorizontalLayout buttons = new HorizontalLayout(saveUserButton, updateUserButton, deleteUserButton, cancelUserButton);
        saveUserButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        email.setClearButtonVisible(true);
        email.setPlaceholder("Use only valid address !");
        password.setClearButtonVisible(true);
        pesel.setClearButtonVisible(true);
        pesel.setPlaceholder("Must be 9 characters !");
        add(email, password, pesel, buttons);
        binder.bindInstanceFields(this);
        adminUsersView = adminUsersView2;
        saveUserButton.addClickListener(event -> saveUser());
        updateUserButton.addClickListener(event -> updateUser());
        deleteUserButton.addClickListener(event -> deleteUser());
        cancelUserButton.addClickListener(event -> setUser(null));

        notification.setDuration(4000);
        notification.setPosition(Notification.Position.MIDDLE);
    }

    private void updateUser() {
        UserDto userDto = binder.getBean();
        userService.update(userDto);
        adminUsersView.refreshUsers();
        setUser(null);
        notification.setText("User updated");
        notification.open();
    }

    private void saveUser() {
        UserDto userDto = binder.getBean();
        long size = userService.count();
        if (userDto.getPesel().length() == 9) {
            userService.save(userDto);
            adminUsersView.refreshUsers();
            if (userService.count() > size) {
                setUser(null);
                notification.setText("User created");
                notification.open();
            } else {
                notification.setText("Check email !");
                notification.open();
            }
        } else {
            notification.setText("Check pesel !");
            notification.open();
        }
    }

    private void deleteUser() {
        UserDto userDto = binder.getBean();
        userService.delete(userDto.getId());
        adminUsersView.refreshUsers();
        setUser(null);
        notification.setText("User deleted");
        notification.open();
    }

    public void setUser(UserDto userDto) {
        binder.setBean(userDto);
        if (userDto == null) {
            setVisible(false);
        } else {
            setVisible(true);
            email.focus();
        }
    }
}
