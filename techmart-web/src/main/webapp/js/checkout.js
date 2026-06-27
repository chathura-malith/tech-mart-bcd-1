function saveNewAddress() {
    const streetAddress = document.getElementById('streetAddress').value.trim();
    const city = document.getElementById('city').value.trim();
    const postalCode = document.getElementById('postalCode').value.trim();
    const errorAlert = document.getElementById('addressErrorAlert');

    errorAlert.classList.add('d-none');

    if (!streetAddress || !city || !postalCode) {
        errorAlert.innerText = "Please fill in all fields.";
        errorAlert.classList.remove('d-none');
        return;
    }

    fetch(contextPath + '/add-address', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: 'streetAddress=' + encodeURIComponent(streetAddress) +
            '&city=' + encodeURIComponent(city) +
            '&postalCode=' + encodeURIComponent(postalCode)
    })
        .then(response => response.json())
        .then(data => {
            if (data.status === 'unauthorized') {
                window.location.href = '${pageContext.request.contextPath}/login.jsp';
                return;
            }

            if (data.status === 'success') {
                location.reload();
            } else {
                errorAlert.innerText = data.message || "Failed to add address. Please try again.";
                errorAlert.classList.remove('d-none');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            errorAlert.innerText = "Something went wrong! Please try again.";
            errorAlert.classList.remove('d-none');
        });

}

document.addEventListener("DOMContentLoaded", function() {

    const checkoutForm = document.getElementById("checkoutForm");
    const btnConfirmOrder = document.getElementById("btnConfirmOrder");
    const paymentModalElement = document.getElementById('paymentModal');
    let paymentModal;

    if(typeof bootstrap !== 'undefined') {
        paymentModal = new bootstrap.Modal(paymentModalElement);
    }

    btnConfirmOrder.addEventListener("click", function(event) {
        event.preventDefault();
        paymentModal.show();
    });

    document.getElementById("btnProcessPayment").addEventListener("click", function() {

        const cardNum = document.getElementById("modalCardNumber").value;
        if(cardNum.trim() === "") {
            alert("Please enter a valid card number!");
            return;
        }

        let hiddenCardInput = document.getElementById("hiddenCardNumber");
        if (!hiddenCardInput) {
            hiddenCardInput = document.createElement("input");
            hiddenCardInput.type = "hidden";
            hiddenCardInput.name = "cardNumber";
            hiddenCardInput.id = "hiddenCardNumber";
            checkoutForm.appendChild(hiddenCardInput);
        }
        hiddenCardInput.value = cardNum;

        paymentModal.hide();
        document.getElementById("loadingOverlay").classList.remove("d-none");

        checkoutForm.submit();
    });
});