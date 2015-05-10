package com.example.ui;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@SpringUI
@SpringView(name = LoginView.NAME)
public class LoginView extends CustomComponent implements View {

	public static final String NAME = "";

	private final TextField user;

	private final PasswordField password;

	private final Button loginButton;

	public LoginView() {
		setSizeFull();

		user = new TextField("ユーザー名:");
		user.setWidth("300px");
		user.setRequired(true);
		user.setInputPrompt("例：user@example.com");
		user.addValidator(new EmailValidator("ユーザ名かパスワードが不正です"));
		user.setInvalidAllowed(false);

		password = new PasswordField("パスワード:");
		password.setWidth("300px");
		password.addValidator(new PasswordValidator());
		password.setRequired(true);
		password.setValue("");
		password.setNullRepresentation("");

		loginButton = new Button("ログイン", e -> {
			if (!user.isValid() || !password.isValid()) {
				return;
			}

			String username = user.getValue();
			String password = this.password.getValue();

			boolean isValid = username.equals("test@test.com")
												&& password.equals("passw0rd");

			if (isValid) {
				getSession().setAttribute("user", username);
				getUI().getNavigator().navigateTo(LoginedView.NAME);
			} else {
				this.password.setValue(null);
				this.password.focus();
			}
		});

		VerticalLayout fields = new VerticalLayout(user, password, loginButton);
		fields.setCaption("ログインしてください (test@test.com/passw0rd)");
		fields.setSpacing(true);
		fields.setMargin(new MarginInfo(true, true, true, false));
		fields.setSizeUndefined();

		VerticalLayout viewLayout = new VerticalLayout(fields);
		viewLayout.setSizeFull();
		viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
		viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
		setCompositionRoot(viewLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		user.focus();
	}

	private static final class PasswordValidator extends AbstractValidator<String> {

		public PasswordValidator() {
			super("ユーザ名かパスワードが不正です");
		}

		@Override
		protected boolean isValidValue(String value) {
			return value != null ? value.length() >= 8 && value.matches(".*\\d.*") : false;
		}

		@Override
		public Class<String> getType() {
			return String.class;
		}
	}

}