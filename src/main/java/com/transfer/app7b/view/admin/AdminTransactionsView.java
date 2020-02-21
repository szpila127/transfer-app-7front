package com.transfer.app7b.view.admin;

import com.transfer.app7b.domain.dto.AccountDto;
import com.transfer.app7b.domain.dto.TransactionDto;
import com.transfer.app7b.form.AccountAdminForm;
import com.transfer.app7b.form.TransactionAdminForm;
import com.transfer.app7b.service.TransactionService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route(value = "admin/transactions")
public class AdminTransactionsView extends VerticalLayout {

    private Button homeButton = new Button("HOME",  VaadinIcon.HOME.create());
    private Button usersButton = new Button("USERS");
    private Button accountsButton = new Button("ACCOUNTS");
    private Button transactionsButton = new Button("TRANSACTIONS");
    private Button appEventsButton = new Button("APPLICATION EVENTS");
    private TransactionService transactionService = TransactionService.getInstance();
    private Grid<TransactionDto> gridTransaction = new Grid<>(TransactionDto.class);
    private TextField filterTransactionsByCurrency = new TextField();
    private TextField filterTransactionsByDate = new TextField();
    private TextField filterTransactionsByAccountOutId = new TextField();
    private TextField filterTransactionsByAccountInId = new TextField();
    private TransactionAdminForm transactionAdminForm = new AccountAdminForm(this);

    public AdminTransactionsView() {
        filterTransactionsByCurrency.setPlaceholder("Filter by currency...");
        filterTransactionsByCurrency.setClearButtonVisible(true);
        filterTransactionsByCurrency.setValueChangeMode(ValueChangeMode.EAGER);
        filterTransactionsByCurrency.addValueChangeListener(e -> updateTransactionsByCurrency());

        filterTransactionsByDate.setPlaceholder("Filter by date...");
        filterTransactionsByDate.setClearButtonVisible(true);
        filterTransactionsByDate.setValueChangeMode(ValueChangeMode.EAGER);
        filterTransactionsByDate.addValueChangeListener(e -> updateTransactionsByDate());

        filterTransactionsByAccountOutId.setPlaceholder("Filter by Account Out ID...");
        filterTransactionsByAccountOutId.setClearButtonVisible(true);
        filterTransactionsByAccountOutId.setValueChangeMode(ValueChangeMode.EAGER);
        filterTransactionsByAccountOutId.addValueChangeListener(e -> updateTransactionsByAccounyOutId());

        filterTransactionsByAccountInId.setPlaceholder("Filter by Account In ID...");
        filterTransactionsByAccountInId.setClearButtonVisible(true);
        filterTransactionsByAccountInId.setValueChangeMode(ValueChangeMode.EAGER);
        filterTransactionsByAccountInId.addValueChangeListener(e -> updateTransactionsByAccounyInId());

        gridTransaction.addColumns("id", "amount", "currency", "date", "accountOutId", "accountInId");
        gridTransaction.getColumnByKey("id").setHeader("ID");
        homeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        homeButton.addClickListener(event -> {
            homeButton.getUI().ifPresent(ui -> ui.navigate(""));
        });
        Button addNewTransaction = new Button("Add new transaction",  VaadinIcon.PLUS_CIRCLE.create());
        addNewTransaction.addClickListener(event -> {
            gridTransaction.asSingleSelect().clear();
            transactionAdminForm.saveAccountButton.setVisible(true);
            transactionAdminForm.deleteAccountButton.setVisible(false);
            transactionAdminForm.updateAccountButton.setVisible(false);
            transactionAdminForm.setAccount(new AccountDto());
        });
    }

    private void updateTransactionsByCurrency() {
        gridTransaction.setItems(transactionService.filterByCurrency(filterTransactionsByCurrency.getValue()));
    }

    private void updateTransactionsByDate() {
        gridTransaction.setItems(transactionService.filterByDate(filterTransactionsByDate.getValue()));
    }

    private void updateTransactionsByAccounyOutId() {
        gridTransaction.setItems(transactionService.filterByAccountOutId(filterTransactionsByAccountOutId.getValue()));
    }

    private void updateTransactionsByAccounyInId() {
        gridTransaction.setItems(transactionService.filterByAccountInId(filterTransactionsByAccountInId.getValue()));
    }

    public void refreshTransactions() {
        transactionService.fetchAll();
        gridTransaction.setItems(transactionService.getTransactionDtos());
    }
}
