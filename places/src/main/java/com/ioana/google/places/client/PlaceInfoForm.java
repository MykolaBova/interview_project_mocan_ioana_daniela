package com.ioana.google.places.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.ioana.google.places.dto.PlaceDTO;

public class PlaceInfoForm extends Composite {

	private static Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, PlaceInfoForm> {
	}

	@UiField
	TextBox scopeBox;

	@UiField
	TextBox iconLinkBox;

	@UiField
	TextBox vicinityBox;

	/*
	 * @UiField ListBox categoryBox;
	 */
	@UiField
	Button createButton;

	@UiField
	Button updateButton;

	private PlaceDTO placeInfo;

	public PlaceInfoForm() {
		initWidget(uiBinder.createAndBindUi(this));

		// Add the categories to the category box.
		/*
		 * final Category[] categories =
		 * ContactDatabase.get().queryCategories(); for (Category category :
		 * categories) { categoryBox.addItem(category.getDisplayName()); }
		 */

		// Initialize the contact to null.
		setPlace(null);

		// Handle events.
		updateButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (placeInfo == null) {
					return;
				}

				// Update the contact.
				placeInfo.setVicinity(vicinityBox.getText());
				placeInfo.setScope(scopeBox.getText());
				placeInfo.setIconLink(iconLinkBox.getText());
				// int categoryIndex = categoryBox.getSelectedIndex();

				// Update the views.
				// ContactDatabase.get().refreshDisplays();
			}
		});
		createButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				/*
				 * int categoryIndex = categoryBox.getSelectedIndex(); Category
				 * category = categories[categoryIndex];
				 */
				placeInfo = new PlaceDTO();
				placeInfo.setScope(scopeBox.getText());
				placeInfo.setVicinity(vicinityBox.getText());
				placeInfo.setIconLink(iconLinkBox.getText());
				// ContactDatabase.get().addContact(placeInfo);
				setPlace(placeInfo);
			}
		});
	}

	public void setPlace(PlaceDTO contact) {
		this.placeInfo = contact;
		updateButton.setEnabled(contact != null);
		if (contact != null) {

			scopeBox.setText(contact.getScope());
			vicinityBox.setText(contact.getVicinity());
			iconLinkBox.setText(contact.getIconLink());

			// Category category = contact.getCategory();
			/*
			 * Category[] categories = ContactDatabase.get().queryCategories();
			 * for (int i = 0; i < categories.length; i++) { if (category ==
			 * categories[i]) { categoryBox.setSelectedIndex(i); break; } }
			 */
		}
	}
}
