$(document).ready(function() {
    const email = localStorage.getItem('email');

    if (!email) {
        console.error('Email not found in localStorage.');
        return;
    }

    const url = `http://localhost:911/users/profile?email=${encodeURIComponent(email)}`;

    $.ajax({
        url: url,
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
        success: function(userProfile) {
            displayUserProfile(userProfile);
        },
        error: function(error) {
            console.error('Error fetching user profile:', error);
        }
    });

    function displayUserProfile(userProfile) {
        $('#profileUsername').text(userProfile.username);
        $('#usernameInput').val(userProfile.username);
        $('#firstName').val(userProfile.firstName);
        $('#lastName').val(userProfile.lastName);
        $('#email').val(userProfile.email);

        // Display the username in the navbar dropdown
        $('#username').text(userProfile.username);

        $('#editButton').click(function() {
            toggleEditMode(true);
        });

        $('#saveButton').click(function() {
            saveChanges();
        });

        $('#cancelButton').click(function() {
            toggleEditMode(false);
        });

        $('#showPassword').click(function() {
            togglePasswordVisibility();
        });
    }

    function toggleEditMode(editMode) {
        $('#usernameInput, #firstName, #lastName, #email, #password').prop('disabled', !editMode);
        $('#editButton').toggle(!editMode);
        $('#editButtons').toggle(editMode);
    }

    function saveChanges() {
        const updatedProfile = {
            username: $('#usernameInput').val(),
            firstName: $('#firstName').val(),
            lastName: $('#lastName').val(),
            email: $('#email').val(),
            password: $('#password').val()
        };

        $.ajax({
            url: `http://localhost:911/users/update`,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(updatedProfile),
            success: function(response) {
                alert('Profile updated successfully');
                displayUserProfile(updatedProfile);
                toggleEditMode(false);
            },
            error: function(error) {
                console.error('Error updating profile:', error);
                alert('Failed to update profile');
            }
        });
    }

    function togglePasswordVisibility() {
        const passwordInput = $('#password');
        const type = passwordInput.attr('type') === 'password' ? 'text' : 'password';
        passwordInput.attr('type', type);
        $('#showPassword').text(type === 'password' ? 'Show' : 'Hide');
    }
});
