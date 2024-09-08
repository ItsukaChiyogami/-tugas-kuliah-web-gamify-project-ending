$(document).ready(function() {
    console.log("Email:", localStorage.getItem('email'));

    // Function to fetch user profile data from the server
    function fetchUserProfile() {
        const email = localStorage.getItem('email');

        if (!email) {
            console.error('Email not found in localStorage.');
            showLoginLink(); // Show login link if email is not found
            return; 
        }

        const url = `http://localhost:911/users/profile?email=${encodeURIComponent(email)}`;

        console.log('Fetching user profile with URL:', url);

        // AJAX request to fetch user profile
        $.ajax({
            url: url,
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            success: function(userProfile) {
                console.log('Raw response:', userProfile);

                // Check if response is empty
                if (!userProfile) {
                    console.error('Empty response received from server.');
                    alert('Empty response received from server. Please try again later.');
                    showLoginLink(); // Show login link on empty response
                    return;
                }

                console.log('User profile fetched successfully:', userProfile);

                // Populate user profile data in the HTML
                populateUserProfile(userProfile);
            },
            error: function(xhr, status, error) {
                console.error('Error occurred while fetching user profile:', error);
                console.log('Status:', status);
                console.log('XHR:', xhr);
                if (xhr.status === 401) {
                    alert('Unauthorized. Please log in again.');
                    handleLogout(); // Handle logout on unauthorized error
                } else if (xhr.status === 404) {
                    alert('User profile not found.');
                    handleLogout(); // Handle logout if user profile not found
                } else {
                    alert('An error occurred. Please try again later.');
                    showLoginLink(); // Show login link for other errors
                }
            }
        });
    }

    // Function to populate user profile data in HTML
    function populateUserProfile(userProfile) {
        console.log('Populating user profile:', userProfile);

        // Check if userProfile is defined and not empty
        if (!userProfile || Object.keys(userProfile).length === 0) {
            console.error('User profile data is empty or undefined');
            // You can show an error message or handle this case appropriately
            return;
        }

        // Update UI to show logged-in user's profile dropdown
        $('#username').text(userProfile.username || 'N/A');
        $('#user-points').text(userProfile.points || 'N/A');
        $('#profileDropdown').show();
        $('#loginLink').hide();

        console.log('User profile populated');
    }

    // Function to show login link and hide profile dropdown
    function showLoginLink() {
        $('#loginLink').show();
        $('#profileDropdown').hide();
    }

    // Function to handle logout
    function handleLogout() {
        // Clear JWT token and email from localStorage
        localStorage.removeItem('jwtToken');
        localStorage.removeItem('email');
        
        // Redirect to landing page after logout
        window.location.href = "../html/landingpage.html";
    }

    // Call fetchUserProfile function when the document is ready
    fetchUserProfile();

    // Attach logout handler to the logout link
    $(document).on('click', '#logoutLink', function(event) {
        event.preventDefault();
        handleLogout();
    });

    // Toggle dropdown menu
    $(document).on('click', '.dropdown-toggle', function(event) {
        event.preventDefault();
        $(this).next('.dropdown-menu').slideToggle();
    });

    // Close dropdown menu on outside click
    $(document).on('click', function(event) {
        if (!$(event.target).closest('.dropdown-toggle').length) {
            $('.dropdown-menu').slideUp();
        }
    });
});
