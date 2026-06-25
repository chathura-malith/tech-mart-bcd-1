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