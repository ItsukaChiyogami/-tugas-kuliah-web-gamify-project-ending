document.addEventListener('DOMContentLoaded', function() {
    const searchForm = document.getElementById('searchForm');
    const searchInput = document.getElementById('searchInput');

    searchForm.addEventListener('submit', function(event) {
        event.preventDefault(); // Prevent the default form submission

        const query = searchInput.value.trim();

        if (query) {
            // Perform the search action here, e.g., redirect to a search results page
            // For demonstration, we will just log the query to the console
            console.log('Search query:', query);

            // Redirect to a search results page (example)
            window.location.href = `/search.html?query=${encodeURIComponent(query)}`;
        } else {
            alert('Please enter a search term.');
        }
    });
});
