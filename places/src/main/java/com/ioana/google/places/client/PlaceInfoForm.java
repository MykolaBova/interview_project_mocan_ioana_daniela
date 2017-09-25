package com.ioana.google.places.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.ioana.google.places.dto.PlaceDTO;

public class PlaceInfoForm extends Composite {

	private static Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, PlaceInfoForm> {
	}

	/** GWT SERVICE **/
	private final GooglePlacesServiceAsync googlePlacesService = GWT
			.create(GooglePlacesService.class);

	@UiField
	TextBox nameBox;

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
				googlePlacesService.updatePlace(placeInfo,
						new AsyncCallback<Void>() {
							public void onFailure(Throwable caught) {

							}

							@Override
							public void onSuccess(Void result) {
								// TODO Auto-generated method stub

							}
						});
				setPlace(placeInfo);
			}
		});
		createButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				/*
				 * int categoryIndex = categoryBox.getSelectedIndex(); Category
				 * category = categories[categoryIndex];
				 */
				PlaceDTO newPlace = new PlaceDTO();
				newPlace.setScope(scopeBox.getText());
				newPlace.setVicinity(vicinityBox.getText());
				newPlace.setIconLink(iconLinkBox.getText());
				newPlace.setName(nameBox.getText());
				newPlace.setCity(placeInfo.getCity());
				googlePlacesService.createPlace(newPlace,
						new AsyncCallback<PlaceDTO>() {
							public void onFailure(Throwable caught) {

							}

							public void onSuccess(PlaceDTO places) {
								// Push the data into the widget.

							}
						});
				setPlace(newPlace);
			}
		});
	}

	public void setPlace(PlaceDTO placeDTO) {
		this.placeInfo = placeDTO;
		updateButton.setEnabled(placeDTO != null);
		if (placeDTO != null) {

			scopeBox.setText(placeDTO.getScope());
			vicinityBox.setText(placeDTO.getVicinity());
			iconLinkBox.setText(placeDTO.getIconLink());
			nameBox.setText(placeDTO.getName());

		}
	}
}
