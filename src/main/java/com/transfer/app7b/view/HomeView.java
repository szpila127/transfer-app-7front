package com.transfer.app7b.view;

import com.transfer.app7b.service.CurrencyService;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;

@Route(value = "")
public class HomeView extends VerticalLayout {

    private NumberField eurField = new NumberField();
    private NumberField usdField = new NumberField();
    private NumberField gbpField = new NumberField();
    private CurrencyService currencyService = new CurrencyService();

    public HomeView() {
        eurField.setLabel("EUR");
        eurField.setReadOnly(true);
        usdField.setLabel("USD");
        usdField.setReadOnly(true);
        gbpField.setLabel("GBP");
        gbpField.setReadOnly(true);
        HorizontalLayout currencies = new HorizontalLayout(eurField, usdField, gbpField);
        add(currencies);
        setSizeFull();
        refreshCurrency();
    }

    public void refreshCurrency() {
        currencyService.getCurrencies();
        eurField.setValue(CurrencyService.EUR);
        usdField.setValue(CurrencyService.USD);
        gbpField.setValue(CurrencyService.GBP);
    }
}
