function setCookie(name, value, days) {
    let expires = "";
    if (days) {
        const date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + encodeURIComponent(value || "") + expires + "; path=/; secure; samesite=strict";
}

$(document).ready(function() {
    $('#loginForm').submit(function(event) {
        event.preventDefault();

        const formData = {
            email: $('#email').val(),
            password: $('#password').val(),
            rememberMe: $('#remember').is(':checked')
        };

        fetch('http://localhost:911/users/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); // Assuming the response is in JSON format
        })
        .then(data => {
            const jwtToken = data.jwtToken;
            alert('Login successful!');

            // Store JWT token in localStorage
            localStorage.setItem('jwtToken', jwtToken);

            // Store email in localStorage
            localStorage.setItem('email', formData.email);

            if (formData.rememberMe) {
                setCookie('jwtToken', jwtToken, 1); // Set cookie for 1 day
                setCookie('email', formData.email, 1); // Set cookie for 1 day
            }

            window.location.href = '../html/discover.html';
        })
        .catch(error => {
            console.error('Error during login:', error);
            if (error instanceof TypeError) {
                alert('Network error. Please check your internet connection.');
            } else {
                alert('Login failed. Please try again later.');
            }
        });
    });
});
