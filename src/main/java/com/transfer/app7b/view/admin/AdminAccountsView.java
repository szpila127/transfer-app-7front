package com.transfer.app7b.view.admin;

import com.transfer.app7b.domain.dto.AccountDto;
import com.transfer.app7b.form.AccountAdminForm;
import com.transfer.app7b.service.AccountService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route(value = "admin/accounts")
public class AdminAccountsView extends VerticalLayout {

    private Button homeButton = new Button("HOME",  VaadinIcon.HOME.create());
    private Button usersButton = new Button("USERS");
    private Button accountsButton = new Button("ACCOUNTS");
    private Button transactionsButton = new Button("TRANSACTIONS");
    private Button appEventsButton = new Button("APPLICATION EVENTS");
    private AccountService accountService = new AccountService();
    private Grid<AccountDto> gridAccount = new Grid<>(AccountDto.class);
    private TextField filterAccountsById = new TextField();
    private TextField filterAccountsByCurrency = new TextField();
    private AccountAdminForm accountAdminForm = new AccountAdminForm(this);


    public AdminAccountsView() {
        filterAccountsById.setPlaceholder("Filter by user ID...");
        filterAccountsById.setClearButtonVisible(true);
        filterAccountsById.setValueChangeMode(ValueChangeMode.EAGER);
        filterAccountsById.addValueChangeListener(e -> updateAccountById());

        filterAccountsByCurrency.setPlaceholder("Filter by currency...");
        filterAccountsByCurrency.setClearButtonVisible(true);
        filterAccountsByCurrency.setValueChangeMode(ValueChangeMode.EAGER);
        filterAccountsByCurrency.addValueChangeListener(e -> updateAccountByCurrency());

        gridAccount.setColumns("id", "balance", "currency", "userId");
        gridAccount.getColumnByKey("id").setHeader("ID");
        gridAccount.getColumnByKey("userId").setHeader("User ID");
        homeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        homeButton.addClickListener(event -> {
            homeButton.getUI().ifPresent(ui -> ui.navigate(""));
        });
        Button addNewAccount = new Button("Add new account",  VaadinIcon.PLUS_CIRCLE.create());
        addNewAccount.addClickListener(event -> {
            gridAccount.asSingleSelect().clear();
            accountAdminForm.saveAccountButton.setVisible(true);
            accountAdminForm.deleteAccountButton.setVisible(false);
            accountAdminForm.updateAccountButton.setVisible(false);
            accountAdminForm.setAccount(new AccountDto());
        });
        HorizontalLayout filterFieldsAccount = new HorizontalLayout(filterAccountsById, filterAccountsByCurrency, addNewAccount);

        usersButton.addClickListener(event -> {
            homeButton.getUI().ifPresent(ui -> ui.navigate("admin/users"));
        });
        accountsButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        transactionsButton.addClickListener(event -> {
            homeButton.getUI().ifPresent(ui -> ui.navigate("admin/transactions"));
        });
        appEventsButton.addClickListener(event -> {
            homeButton.getUI().ifPresent(ui -> ui.navigate("admin/app-events"));
        });
        HorizontalLayout menuButtons = new HorizontalLayout(usersButton, accountsButton, transactionsButton, appEventsButton);

        HorizontalLayout mainContent = new HorizontalLayout(gridAccount, accountAdminForm);
        mainContent.setSizeFull();
        gridAccount.setSizeFull();
        add(homeButton, menuButtons, filterFieldsAccount, mainContent);
        accountAdminForm.setAccount(null);
        setSizeFull();
        refreshAccounts();

        gridAccount.asSingleSelect().addValueChangeListener(event -> {
            accountAdminForm.saveAccountButton.setVisible(false);
            accountAdminForm.deleteAccountButton.setVisible(true);
            accountAdminForm.updateAccountButton.setVisible(true);
            accountAdminForm.setAccount(gridAccount.asSingleSelect().getValue());
        });
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
