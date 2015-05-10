package com.example.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

@SpringUI
@SpringView(name = LoginedView.NAME)
public class LoginedView extends CustomComponent implements View {

	public static final String NAME = "LoginedView";

	private Label text = new Label();

	private Button logout = new Button("ログアウト", e -> {
		getSession().setAttribute("user", null);
		getUI().getNavigator().navigateTo(NAME);
	});

	public LoginedView() {
		setCompositionRoot(new CssLayout(text, logout));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		String username = String.valueOf(getSession().getAttribute("user"));
		text.setValue("ようこそ、 " + username);
	}
}