package com.transfer.app7b.form;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;

public class TransactionAdminForm extends FormLayout {

    private BigDecimalField amount = new BigDecimalField("Amount");
    private ComboBox<String> currency = new ComboBox<>("Currency");
    private TextField accountOutId = new TextField("Account Out ID");
    private TextField accountInId = new TextField("Account In ID");
}
