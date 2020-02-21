package com.transfer.app7b.view.admin;

import com.transfer.app7b.domain.dto.TransactionDto;
import com.transfer.app7b.form.AccountAdminForm;
import com.transfer.app7b.form.TransactionAdminForm;
import com.transfer.app7b.service.TransactionService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
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

    public void refreshAccounts() {
        transactionService.fetchAll();
        gridTransaction.setItems(transactionService.getTransactionDtos());
    }
}
