package com.transfer.app7b.form;

import com.transfer.app7b.domain.dto.AccountDto;
import com.transfer.app7b.service.AccountService;
import com.transfer.app7b.view.AdminView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class AccountForm extends FormLayout {
    public String[] currencyString = {"PLN", "USD", "EUR", "GBP"};

    private BigDecimalField balance = new BigDecimalField("Balance");
    private ComboBox<String> currency = new ComboBox<>("Currency");
    private TextField userId = new TextField("User ID");
    private Button saveAccount = new Button("Save");
    private Button updateAccount = new Button("Update");
    private Button deleteAccount = new Button("Delete");

    private Binder<AccountDto> binder = new Binder<>(AccountDto.class);
    private AccountService accountService = AccountService.getInstance();
    private AdminView adminView;

    public AccountForm(AdminView adminView2) {
        currency.setItems(currencyString);
        HorizontalLayout buttons = new HorizontalLayout(saveAccount, updateAccount, deleteAccount);
        saveAccount.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(balance, currency, userId, buttons);
        binder.bindInstanceFields(this);
        adminView = adminView2;
        saveAccount.addClickListener(event -> saveAccount());
        updateAccount.addClickListener(event -> updateAccount());
        deleteAccount.addClickListener(event -> deleteAccount());
    }

    private void updateAccount() {
//        saveAccount.setVisible(false);
        AccountDto accountDto = binder.getBean();
        accountService.update(accountDto);
        adminView.refreshAccounts();
        setAccount(null);
    }

    private void saveAccount() {
//        updateAccount.setVisible(false);
        AccountDto accountDto = binder.getBean();
        accountService.save(accountDto);
        adminView.refreshAccounts();
        setAccount(null);
    }

    private void deleteAccount() {
        AccountDto accountDto = binder.getBean();
        accountService.delete(accountDto.getId());
        adminView.refreshAccounts();
        setAccount(null);
    }

    public void setAccount(AccountDto account) {
        binder.setBean(account);
        if (account == null) {
            setVisible(false);
        } else {
            setVisible(true);
            userId.focus();
        }
    }
}
