package com.transfer.app7f.form;

import com.transfer.app7f.domain.Currency;
import com.transfer.app7f.domain.dto.TransactionDto;
import com.transfer.app7f.service.TransactionService;
import com.transfer.app7f.view.admin.AdminTransactionsView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class TransactionAdminForm extends FormLayout {

    private BigDecimalField amount = new BigDecimalField("Amount");
    private ComboBox<String> currency = new ComboBox<>("Currency");
    private TextField accountOutId = new TextField("Account Out ID");
    private TextField accountInId = new TextField("Account In ID");
    public Button saveTransactionButton = new Button("Save");
    public Button returnTransactionButton = new Button("Return");
    public Button updateTransactionButton = new Button("Update");
    public Button deleteTransactionButton = new Button("Delete");
    public Button cancelTransactionButton = new Button("Cancel");
    Notification notification = new Notification();
    private Binder<TransactionDto> binder = new Binder<>(TransactionDto.class);
    public TransactionService transactionService = new TransactionService();

    public AdminTransactionsView adminTransactionsView;

    public TransactionAdminForm(AdminTransactionsView adminTransactionsView2) {
        currency.setItems(Currency.currencyString);
        HorizontalLayout buttons = new HorizontalLayout(saveTransactionButton, returnTransactionButton, updateTransactionButton, deleteTransactionButton, cancelTransactionButton);
        saveTransactionButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        returnTransactionButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        amount.setClearButtonVisible(true);
        accountOutId.setClearButtonVisible(true);
        accountInId.setClearButtonVisible(true);
        add(amount, currency, accountOutId, accountInId, buttons);
        binder.bindInstanceFields(this);
        adminTransactionsView = adminTransactionsView2;
        saveTransactionButton.addClickListener(event -> saveTransaction());
        returnTransactionButton.addClickListener(event -> returnTransaction());
        updateTransactionButton.addClickListener(event -> updateTransaction());
        deleteTransactionButton.addClickListener(event -> deleteTransaction());
        cancelTransactionButton.addClickListener(event -> setTransaction(null));

        notification.setDuration(4000);
        notification.setPosition(Notification.Position.MIDDLE);
    }

    private void returnTransaction() {
        TransactionDto transactionDto = binder.getBean();
        transactionService.returnTransaction(transactionDto.getId());
        adminTransactionsView.refreshTransactions();
        setTransaction(null);
        notification.setText("Transaction returned");
        notification.open();
    }

    private void updateTransaction() {
        TransactionDto transactionDto = binder.getBean();
        transactionService.update(transactionDto);
        adminTransactionsView.refreshTransactions();
        setTransaction(null);
    }

    private void saveTransaction() {
        TransactionDto transactionDto = binder.getBean();
        transactionService.save(transactionDto);
        adminTransactionsView.refreshTransactions();
        setTransaction(null);
        notification.setText("Transaction completed");
        notification.open();
    }

    private void deleteTransaction() {
        TransactionDto transactionDto = binder.getBean();
        transactionService.delete(transactionDto.getId());
        adminTransactionsView.refreshTransactions();
        setTransaction(null);
        notification.setText("Transaction deleted");
        notification.open();
    }

    public void setTransaction(TransactionDto transaction) {
        binder.setBean(transaction);
        if (transaction == null) {
            setVisible(false);
        } else {
            setVisible(true);
            amount.focus();
        }
    }
}
