package com.ioana.google.places.client;

import java.util.ArrayList;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.ioana.google.places.dto.PlaceDTO;
import com.ioana.google.places.shared.FieldVerifier;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GooglePlaces implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/** GWT SERVICE **/
	private final GooglePlacesServiceAsync googlePlacesService = GWT
			.create(GooglePlacesService.class);

	private final Messages messages = GWT.create(Messages.class);

	ProvidesKey<PlaceDTO> keyProvider = new ProvidesKey<PlaceDTO>() {
		public Object getKey(PlaceDTO item) {
			// Always do a null check.
			return (item == null) ? null : item.getReference();
		}
	};

	/**
	 * A custom {@link Cell} used to render a {@link Contact}.
	 */

	private static class PlaceCell extends AbstractCell<PlaceDTO> {

		@Override
		public void render(com.google.gwt.cell.client.Cell.Context context,
				PlaceDTO value, SafeHtmlBuilder sb) {
			if (value != null) {
				sb.appendEscaped(value.getName());
			}

		}
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Button sendButton = new Button(messages.sendButton());
		final TextBox nameField = new TextBox();
		nameField.setText(messages.nameField());
		nameField.setWidth("200");
		final Label errorLabel = new Label();

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Create a CellList using the keyProvider.
		final CellList<PlaceDTO> cellList = new CellList<PlaceDTO>(
				new PlaceCell(), keyProvider);

		// Add a selection model so we can select cells.
		final SingleSelectionModel<PlaceDTO> selectionModel = new SingleSelectionModel<PlaceDTO>(
				keyProvider);
		cellList.setSelectionModel(selectionModel);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					public void onSelectionChange(SelectionChangeEvent event) {
						// show details
						PlaceInfoForm placeDetails = new PlaceInfoForm();
						placeDetails.setPlace(selectionModel
								.getSelectedObject());
						RootPanel.get("placeDetails").clear();
						RootPanel.get("placeDetails").add(placeDetails);
						// placeForm.setPlace(selectionModel.getSelectedObject());
					}
				});

		// Push data into the CellList.

		final VerticalPanel placesPanel = new VerticalPanel();
		placesPanel.setBorderWidth(1);
		placesPanel.setWidth("200");
		placesPanel.add(cellList);

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);
		// Add the widgets to the root panel.

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				searchCity();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					searchCity();
				}
			}

			private void searchCity() {
				// First, we validate the input.
				errorLabel.setText("");
				String city = nameField.getText();
				if (!FieldVerifier.isValidName(city)) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}

				// Then, we send the input to the server.
				sendButton.setEnabled(false);
				textToServerLabel.setText(city);
				serverResponseLabel.setText("");
				googlePlacesService.search(city, 500,
						new AsyncCallback<ArrayList<PlaceDTO>>() {
							public void onFailure(Throwable caught) {
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR);
							}

							public void onSuccess(ArrayList<PlaceDTO> places) {
								// Push the data into the widget.

								cellList.setRowCount(places.size(), true);
								cellList.setRowData(0, places);
								RootPanel.get("placesPanel").add(placesPanel);
								serverResponseLabel
										.removeStyleName("serverResponseLabelError");

							}
						});
			}

		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
	}

}
