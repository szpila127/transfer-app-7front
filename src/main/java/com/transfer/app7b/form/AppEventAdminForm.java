package com.transfer.app7b.form;

import com.transfer.app7b.domain.dto.AppEventDto;
import com.transfer.app7b.service.AppEventService;
import com.transfer.app7b.view.admin.AdminAppEventsView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class AppEventAdminForm extends FormLayout {

    private TextField information = new TextField("Information");
    public Button cancel = new Button("Cancel");

    private Binder<AppEventDto> binder = new Binder<>(AppEventDto.class);
    public AppEventService appEventService = AppEventService.getInstance();

    private AdminAppEventsView adminAppEventsView;

    public AppEventAdminForm(AdminAppEventsView adminAppEventsView2) {
        add(information, cancel);
        binder.bindInstanceFields(this);
        adminAppEventsView = adminAppEventsView2;
        cancel.addClickListener(event -> setAppEvent(null));
    }

    public void setAppEvent(AppEventDto appEvent) {
        binder.setBean(appEvent);
        if (appEvent == null) {
            setVisible(false);
        } else {
            setVisible(true);
        }
    }
}
