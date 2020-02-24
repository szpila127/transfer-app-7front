package com.transfer.app7f.form;

import com.transfer.app7f.domain.dto.AppEventDto;
import com.transfer.app7f.view.admin.AdminAppEventsView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;

public class AppEventAdminForm extends FormLayout {

    private TextArea information = new TextArea("Information");
    public Button cancel = new Button("Cancel");
    private Binder<AppEventDto> binder = new Binder<>(AppEventDto.class);

    public AdminAppEventsView adminAppEventsView;

    public AppEventAdminForm(AdminAppEventsView adminAppEventsView2) {
        information.getStyle().set("maxHight", "400px");
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
