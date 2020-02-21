package com.transfer.app7b.form;

import com.transfer.app7b.domain.dto.TransactionDto;
import com.transfer.app7b.service.TransactionService;
import com.transfer.app7b.view.admin.AdminTransactionsView;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class TransactionAdminForm extends FormLayout {

    private BigDecimalField amount = new BigDecimalField("Amount");
    private ComboBox<String> currency = new ComboBox<>("Currency");
    private TextField accountOutId = new TextField("Account Out ID");
    private TextField accountInId = new TextField("Account In ID");

    private Binder<TransactionDto> binder = new Binder<>(TransactionDto.class);
    public TransactionService transactionService = TransactionService.getInstance();

    private AdminTransactionsView adminTransactionsView;

    public TransactionAdminForm() {
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
    }

    private void deleteAccount() {
        TransactionDto transactionDto = binder.getBean();
        transactionService.delete(transactionDto.getId());
        adminTransactionsView.refreshTransactions();
        setTransaction(null);
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
