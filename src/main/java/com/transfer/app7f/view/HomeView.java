package com.transfer.app7f.view;

import com.transfer.app7f.service.CurrencyService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;

@Route(value = "")
public class HomeView extends VerticalLayout {

    private Button adminButton = new Button("ADMIN", VaadinIcon.KEY.create());
    private NumberField eurField = new NumberField();
    private NumberField usdField = new NumberField();
    private NumberField gbpField = new NumberField();
    private CurrencyService currencyService = new CurrencyService();

    public HomeView() {
        adminButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        adminButton.addClickListener(event -> {
            adminButton.getUI().ifPresent(ui -> ui.navigate("admin/users"));
        });
        eurField.setLabel("EUR");
        eurField.setReadOnly(true);
        usdField.setLabel("USD");
        usdField.setReadOnly(true);
        gbpField.setLabel("GBP");
        gbpField.setReadOnly(true);
        HorizontalLayout currencies = new HorizontalLayout(eurField, usdField, gbpField);
        add(adminButton, currencies);
        setSizeFull();
        refresh();
    }

    public void refresh() {
        currencyService.getCurrencies();
        eurField.setValue(CurrencyService.EUR);
        usdField.setValue(CurrencyService.USD);
        gbpField.setValue(CurrencyService.GBP);
    }
}
