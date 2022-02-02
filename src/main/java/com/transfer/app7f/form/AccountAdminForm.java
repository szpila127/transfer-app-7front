package com.transfer.app7f.form;

import com.transfer.app7f.domain.Currency;
import com.transfer.app7f.domain.dto.AccountDto;
import com.transfer.app7f.domain.dto.UserDto;
import com.transfer.app7f.service.AccountService;
import com.transfer.app7f.service.UserService;
import com.transfer.app7f.view.admin.AdminAccountsView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.util.Set;

public class AccountAdminForm extends FormLayout {

    private BigDecimalField balance = new BigDecimalField("Balance");
    private ComboBox<String> currency = new ComboBox<>("Currency");
    public TextField userId = new TextField("User ID");
    public Button saveAccountButton = new Button("Save");
    public Button updateAccountButton = new Button("Update");
    public Button deleteAccountButton = new Button("Delete");
    public Button cancelAccountButton = new Button("Cancel");
    Notification notification = new Notification();
    private Binder<AccountDto> binder = new Binder<>(AccountDto.class);
    private AccountService accountService = new AccountService();
    private UserService userService = new UserService();

    private AdminAccountsView adminAccountsView;

    public AccountAdminForm(AdminAccountsView adminAccountsView2) {
        currency.setItems(Currency.currencyString);
        HorizontalLayout buttons = new HorizontalLayout(saveAccountButton, updateAccountButton, deleteAccountButton, cancelAccountButton);
        saveAccountButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        balance.setClearButtonVisible(true);
        userId.setClearButtonVisible(true);
        add(balance, currency, userId, buttons);
        binder.bindInstanceFields(this);
        adminAccountsView = adminAccountsView2;
        saveAccountButton.addClickListener(event -> saveAccount());
        updateAccountButton.addClickListener(event -> updateAccount());
        deleteAccountButton.addClickListener(event -> deleteAccount());
        cancelAccountButton.addClickListener(event -> setAccount(null));

        notification.setDuration(4000);
        notification.setPosition(Notification.Position.MIDDLE);
    }

    private void updateAccount() {
        AccountDto accountDto = binder.getBean();
        accountService.update(accountDto);
        adminAccountsView.refreshAccounts();
        setAccount(null);
        notification.setText("Account updated");
        notification.open();
    }

    private void saveAccount() {
        AccountDto accountDto = binder.getBean();
        accountService.save(accountDto);
        adminAccountsView.refreshAccounts();
        setAccount(null);
        notification.setText("Account created");
        notification.open();
    }

    private void deleteAccount() {
        AccountDto accountDto = binder.getBean();
        accountService.delete(accountDto.getId());
        adminAccountsView.refreshAccounts();
        setAccount(null);
        notification.setText("Account deleted");
        notification.open();
    }

    public void setAccount(AccountDto account) {
        binder.setBean(account);
        if (account == null) {
            setVisible(false);
        } else {
            setVisible(true);
            balance.focus();
        }
    }

//    private boolean userExist(String id) {
//        if (userService.getUserDtos().contains(new UserDto().);
//    }
}
