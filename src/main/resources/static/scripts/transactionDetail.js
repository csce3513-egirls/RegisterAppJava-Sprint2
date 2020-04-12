document.addEventListener("DOMContentLoaded", () => {
	const transactionLookupCodeElement = getTransactionLookupCodeElement(); 

	transactionLookupCodeElement.addEventListener("keypress", transactionLookupCodeKeypress);
	
	getSaveActionElement().addEventListener("click", saveActionClick);
	
	if (!transactionLookupCodeElement.disabled) {
		transactionLookupCodeElement.focus();
		transactionLookupCodeElement.select();
	}
});

function transactionLookupCodeKeypress(event) {
	if (event.which !== 13) { // Enter key
		return;
    }
    saveActionClick();
}

// Save
function saveActionClick(event) {
	if (!validateSave()) {
		return;
	}

	const saveActionElement = event.target;
	saveActionElement.disabled = true;
};

function validateSave() {
	const lookupCode = getTransactionLookupCode();
	if ((lookupCode == null) || (lookupCode.trim() === "")) {
		displayError("Please provide a valid transaction lookup code.");
		return false;
	}

	return true;
}
// End save

// Getters and setters
function getSaveActionElement() {
	return document.getElementById("searchButton");
}

function getTransactionLookupCode() {
	return getTransactionLookupCodeElement().value;
}
function getTransactionLookupCodeElement() {
	return document.getElementById("transactionLookupCode");
}
// End getters and setters
