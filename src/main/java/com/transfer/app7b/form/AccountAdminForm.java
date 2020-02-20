package com.transfer.app7b.form;

import com.transfer.app7b.domain.dto.AccountDto;
import com.transfer.app7b.service.AccountService;
import com.transfer.app7b.view.admin.AdminAccountsView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class AccountAdminForm extends FormLayout {
    public String[] currencyString = {"PLN", "USD", "EUR", "GBP"};

    private BigDecimalField balance = new BigDecimalField("Balance");
    private ComboBox<String> currency = new ComboBox<>("Currency");
    private TextField userId = new TextField("User ID");
    public Button saveAccountB = new Button("Save");
    public Button updateAccountB = new Button("Update");
    public Button deleteAccountB = new Button("Delete");
    public Button cancelAccountB = new Button("Cancel");

    private Binder<AccountDto> binder = new Binder<>(AccountDto.class);
    private AccountService accountService = AccountService.getInstance();

    private AdminAccountsView adminAccountsView;

    public AccountAdminForm(AdminAccountsView adminAccountsView2) {
        currency.setItems(currencyString);
        HorizontalLayout buttons = new HorizontalLayout(saveAccountB, updateAccountB, deleteAccountB, cancelAccountB);
        saveAccountB.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(balance, currency, userId, buttons);
        binder.bindInstanceFields(this);
        adminAccountsView = adminAccountsView2;
        saveAccountB.addClickListener(event -> saveAccount());
        updateAccountB.addClickListener(event -> updateAccount());
        deleteAccountB.addClickListener(event -> deleteAccount());
        cancelAccountB.addClickListener(event -> setAccount(null));
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
