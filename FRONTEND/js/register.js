$(document).ready(function() {
    $('#registrationForm').submit(function(event) {
        event.preventDefault();

        const formData = {
            username: $('#username').val(),
            email: $('#email').val(),
            firstName: $('#firstName').val(),
            lastName: $('#lastName').val(),
            password: $('#password').val(),
            confirmPassword: $('#confirmPassword').val()
        };

        // Perform client-side validation
        if (formData.password !== formData.confirmPassword) {
            alert('Passwords do not match');
            return;
        }

        $.ajax({
            type: 'POST',
            url: 'http://localhost:911/users/register', // Sesuaikan URL dengan konfigurasi Anda
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                // Handle successful registration
                alert('Registration successful!');
                // Example: redirect to login page
                window.location.href = '../html/login.html';
            },
            error: function(xhr, status, error) {
                // Handle error responses
                if (xhr.status === 400) {
                    alert('User registration failed: User already exists or invalid data');
                } else {
                    alert('Registration failed. Please try again later.');
                }
            }
        });
    });
});
