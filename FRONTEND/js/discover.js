document.addEventListener('DOMContentLoaded', function() {
    const recommendedPlaces = [
        { id: 1, image: '../images/toraja.png', title: 'Tana Toraja', description: 'Indonesia, Sulawesi Selatan', link: 'toraja.html'},
        { id: 2, image: '../images/mks.jpg', title: 'Kota Makassar', description: 'Indonesia, Sulawesi Selatan', link: 'toraja.html'},
        { id: 3, image: '../images/malino.jpg', title: 'Malino', description: 'Indonesia, Sulawesi Selatan', link: 'toraja.html'},
        { id: 4, image: '../images/bira.jpg', title: 'Pantai Bira', description: 'Indonesia, Sulawesi Selatan', link: 'toraja.html'},
        { id: 5, image: '../images/samalona.jpg', title: 'Pulau Samalona', description: 'Indonesia, Sulawesi Selatan', link: 'toraja.html'},
    ];

    const trendingPlaces = [
        { id: 1, image: '../images/toraja.png', title: 'Tana Toraja', description: 'Indonesia, Sulawesi Selatan', link: 'toraja.html'},
        { id: 2, image: '../images/mks.jpg', title: 'Kota Makassar', description: 'Indonesia, Sulawesi Selatan', link: 'toraja.html'},
        { id: 3, image: '../images/malino.jpg', title: 'Malino', description: 'Indonesia, Sulawesi Selatan', link: 'toraja.html'},
        { id: 4, image: '../images/bira.jpg', title: 'Pantai Bira', description: 'Indonesia, Sulawesi Selatan', link: 'toraja.html'},
        { id: 5, image: '../images/samalona.jpg', title: 'Pulau Samalona', description: 'Indonesia, Sulawesi Selatan', link: 'toraja.html'},
    ];

    const topSpots = [
        { id: 1, image: '../images/toraja.png', title: 'Tana Toraja', description: 'Indonesia, Sulawesi Selatan', link: 'toraja.html'},
        { id: 2, image: '../images/mks.jpg', title: 'Kota Makassar', description: 'Indonesia, Sulawesi Selatan', link: 'toraja.html'},
        { id: 3, image: '../images/malino.jpg', title: 'Malino', description: 'Indonesia, Sulawesi Selatan', link: 'toraja.html'},
        { id: 4, image: '../images/bira.jpg', title: 'Pantai Bira', description: 'Indonesia, Sulawesi Selatan', link: 'toraja.html'},
        { id: 5, image: '../images/samalona.jpg', title: 'Pulau Samalona', description: 'Indonesia, Sulawesi Selatan', link: 'toraja.html'},
    ];

    function createPlaceCard(place) {
        return `
            <div class="col-lg-3 col-md-4 mb-4">
                <div class="card h-110">
                <div class="card-img">
                    <img src="${place.image}">
                </div>
                    <div class="card-body">
                        <h5 class="card-title text-white">${place.title}</h5>
                        <p class="card-text text-white  ">${place.description}</p>
                        <a href="${place.link}" class="btn btn-redeem">Lihat Misi</a>
                    </div>
                </div>
            </div>
        `;
    }

    function injectPlaces(places, containerId) {
        const container = document.getElementById(containerId);
        places.forEach(place => {
            container.innerHTML += createPlaceCard(place);
        });
    }

    injectPlaces(recommendedPlaces, 'recommended-container');
    injectPlaces(trendingPlaces, 'trending-container');
    injectPlaces(topSpots, 'top-spots-container');
});

document.addEventListener('DOMContentLoaded', function () {
    const searchForm = document.getElementById('searchForm');

    searchForm.addEventListener('submit', function (event) {
        event.preventDefault(); // Prevent the form from submitting in the traditional way
        const searchQuery = document.getElementById('searchQuery').value.trim();

        if (searchQuery) {
            // Perform your search logic here
            // For demonstration, we'll just log the search query to the console
            console.log('Searching for:', searchQuery);

            // You can redirect to a search results page or perform an AJAX request here
            // Example: window.location.href = `/search?query=${encodeURIComponent(searchQuery)}`;
        } else {
            alert('Please enter a search query');
        }
    });
});

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


