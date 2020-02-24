package com.transfer.app7f.form;

import com.transfer.app7f.domain.Currency;
import com.transfer.app7f.domain.dto.AccountDto;
import com.transfer.app7f.service.AccountService;
import com.transfer.app7f.view.admin.AdminAccountsView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class AccountAdminForm extends FormLayout {

    private BigDecimalField balance = new BigDecimalField("Balance");
    private ComboBox<String> currency = new ComboBox<>("Currency");
    private TextField userId = new TextField("User ID");
    public Button saveAccountButton = new Button("Save");
    public Button updateAccountButton = new Button("Update");
    public Button deleteAccountButton = new Button("Delete");
    public Button cancelAccountButton = new Button("Cancel");
    private Binder<AccountDto> binder = new Binder<>(AccountDto.class);
    private AccountService accountService = new AccountService();

    private AdminAccountsView adminAccountsView;

    public AccountAdminForm(AdminAccountsView adminAccountsView2) {
        currency.setItems(Currency.currencyString);
        HorizontalLayout buttons = new HorizontalLayout(saveAccountButton, updateAccountButton, deleteAccountButton, cancelAccountButton);
        saveAccountButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(balance, currency, userId, buttons);
        binder.bindInstanceFields(this);
        adminAccountsView = adminAccountsView2;
        saveAccountButton.addClickListener(event -> saveAccount());
        updateAccountButton.addClickListener(event -> updateAccount());
        deleteAccountButton.addClickListener(event -> deleteAccount());
        cancelAccountButton.addClickListener(event -> setAccount(null));
    }

    private void updateAccount() {
        AccountDto accountDto = binder.getBean();
        accountService.update(accountDto);
        adminAccountsView.refreshAccounts();
        setAccount(null);
    }

    private void saveAccount() {
        AccountDto accountDto = binder.getBean();
        accountService.save(accountDto);
        adminAccountsView.refreshAccounts();
        setAccount(null);
    }

    private void deleteAccount() {
        AccountDto accountDto = binder.getBean();
        accountService.delete(accountDto.getId());
        adminAccountsView.refreshAccounts();
        setAccount(null);
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
}
