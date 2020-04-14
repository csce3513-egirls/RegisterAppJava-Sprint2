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

// Save // edit to validate search is not empty?
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

//partial search 
function search() {
    document.getElementById("dropdown").classList.toggle("show");
  }
  
  function filterInput() {
    var input, filter, a, i;
    input = document.getElementById("userInput");
    filter = input.value.toUpperCase();
    div = document.getElementById("dropdown");
    a = div.getElementsByTagName("li");
    for (i = 0; i < a.length; i++) {
      txtValue = a[i].textContent || a[i].innerText;
      if (txtValue.toUpperCase().indexOf(filter) > -1) {
        a[i].style.display = "";
      } else {
        a[i].style.display = "none";
      }
    }
  }
