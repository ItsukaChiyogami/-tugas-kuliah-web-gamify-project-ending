// scripts.js

document.addEventListener('DOMContentLoaded', function() {
    const searchForm = document.getElementById('searchForm');
    const searchQueryInput = document.getElementById('searchQuery');
    const searchTermElement = document.getElementById('searchTerm');
    const resultsContainer = document.getElementById('resultsContainer');

    // Dummy data for search results
    const results = [
        { title: 'Toraja', description: 'Description for Place 1', image: 'https://via.placeholder.com/150' },
        { title: 'Place 2', description: 'Description for Place 2', image: 'https://via.placeholder.com/150' },
        { title: 'Place 3', description: 'Description for Place 3', image: 'https://via.placeholder.com/150' },
        // Add more results as needed
    ];

    // Function to render search results
    function renderResults(query) {
        searchTermElement.textContent = query;
        resultsContainer.innerHTML = '';
        
        const filteredResults = results.filter(result => result.title.toLowerCase().includes(query.toLowerCase()));
        
        filteredResults.forEach(result => {
            const resultCard = document.createElement('div');
            resultCard.className = 'card';
            resultCard.innerHTML = `
                <img src="${result.image}" alt="${result.title}">
                <h3>${result.title}</h3>
                <p>${result.description}</p>
            `;
            resultsContainer.appendChild(resultCard);
        });
    }

    // Handle form submission
    searchForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const query = searchQueryInput.value;
        if (query) {
            renderResults(query);
        }
    });

    // Get query parameter from URL (for when user navigates directly to this page with a query)
    const urlParams = new URLSearchParams(window.location.search);
    const query = urlParams.get('q');
    if (query) {
        searchQueryInput.value = query;
        renderResults(query);
    }
});

document.addEventListener("DOMContentLoaded", function() {
    const readMoreLinks = document.querySelectorAll('.read-more');

    readMoreLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            const description = this.previousElementSibling;
            if (description.style.display === 'none' || description.style.display === '') {
                description.style.display = 'block';
                this.textContent = 'Read Less';
            } else {
                description.style.display = 'none';
                this.textContent = 'Read More';
            }
        });
    });
});