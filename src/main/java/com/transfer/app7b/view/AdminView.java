package com.transfer.app7b.view;

import com.transfer.app7b.domain.dto.AccountDto;
import com.transfer.app7b.form.AccountForm;
import com.transfer.app7b.service.AccountService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route(value = "admin")
public class AdminView extends VerticalLayout {

    private AccountService accountService = AccountService.getInstance();
    private Grid<AccountDto> gridAccount = new Grid<>(AccountDto.class);
    private Label accountsLabel = new Label("ACCOUNTS");
    private TextField filterAccountsById = new TextField();
    private TextField filterAccountsByCurrency = new TextField();
    private AccountForm accountForm = new AccountForm(this);
    private Button addNewAccount = new Button("Add new account");

    public AdminView() {
        filterAccountsById.setPlaceholder("Filter by id");
        filterAccountsById.setClearButtonVisible(true);
        filterAccountsById.setValueChangeMode(ValueChangeMode.EAGER);
        filterAccountsById.addValueChangeListener(e -> updateAccountById());
        filterAccountsByCurrency.setPlaceholder("Filter by currency");
        filterAccountsByCurrency.setClearButtonVisible(true);
        filterAccountsByCurrency.setValueChangeMode(ValueChangeMode.EAGER);
        filterAccountsByCurrency.addValueChangeListener(e -> updateAccountByCurrency());
        gridAccount.setColumns("userId", "balance", "currency");
        addNewAccount.addClickListener(event -> {
            gridAccount.asSingleSelect().clear();
            accountForm.setAccount(new AccountDto());
        });

        HorizontalLayout filterFieldsAccount = new HorizontalLayout(filterAccountsById, filterAccountsByCurrency, addNewAccount);

        HorizontalLayout mainContent = new HorizontalLayout(gridAccount, accountForm);
        mainContent.setSizeFull();
        gridAccount.setSizeFull();
        gridAccount.setHeight("270px");
        add(accountsLabel,filterFieldsAccount, mainContent);
        accountForm.setAccount(null);
        setSizeFull();
        refreshAccounts();

        gridAccount.asSingleSelect().addValueChangeListener(event -> accountForm.setAccount(gridAccount.asSingleSelect().getValue()));
    }

    private void updateAccountById() {
        gridAccount.setItems(accountService.filterByUserId(filterAccountsById.getValue()));
    }

    private void updateAccountByCurrency() {
        gridAccount.setItems(accountService.filterByCurrency(filterAccountsByCurrency.getValue()));
    }

    public void refreshAccounts() {
        accountService.fetchAll();
        gridAccount.setItems(accountService.getAccountDtos());
    }
}
