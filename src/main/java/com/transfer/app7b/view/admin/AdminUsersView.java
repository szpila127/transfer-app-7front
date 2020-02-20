package com.transfer.app7b.view.admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "admin/users")
public class AdminUsersView extends VerticalLayout {

    private Button homeButton = new Button("HOME");
    private Button usersButton = new Button("USERS");
    private Button accountsButton = new Button("ACCOUNTS");
    private Button transactionsButton = new Button("TRANSACTIONS");
    private Button apssEventsButton = new Button("APPLICATION EVENTS");

    public AdminUsersView() {

    }
}
