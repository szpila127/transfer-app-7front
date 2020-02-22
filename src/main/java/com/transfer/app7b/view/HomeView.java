package com.transfer.app7b.view;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;

@Route(value = "")
public class HomeView extends VerticalLayout {

    private NumberField eurField = new NumberField();
    private NumberField usdField = new NumberField();
    private NumberField gbpField = new NumberField();

    public HomeView() {
        eurField.setValue(2.0);
        eurField.setLabel("EUR");
        eurField.setReadOnly(true);
        usdField.setValue(2.0);
        usdField.setLabel("USD");
        usdField.setReadOnly(true);
        gbpField.setValue(2.0);
        gbpField.setLabel("GBP");
        gbpField.setReadOnly(true);
        HorizontalLayout currencies = new HorizontalLayout(eurField, usdField, gbpField);
        add(currencies);
        setSizeFull();
    }
}
