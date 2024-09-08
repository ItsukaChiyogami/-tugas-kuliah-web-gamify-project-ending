document.addEventListener("DOMContentLoaded", function() {
    const cardsContainer = document.getElementById('rewards-container');
    const emailModal = document.getElementById('emailModal');
    const confirmationModal = document.getElementById('confirmationModal');
    const closeButtons = document.querySelectorAll('.close');
    const userPointsElement = document.getElementById('user-points');
    const filterButtons = document.querySelectorAll('.filter-btn');
    const sortButtons = document.querySelectorAll('.sort-btn');
    const insufficientPointsModal = document.getElementById('insufficientPointsModal');
    const submitEmailButton = document.getElementById('submitEmail');
    const userEmailInput = document.getElementById('userEmail');

    const cardData = [
        { id: 1, image: '../images/amazon.jpg', title: 'Amazon Card', description: 'Rp50.000 Gift Card', points: 50, category: 'gift-card' },
        { id: 2, image: '../images/amazon.jpg', title: 'Amazon Card', description: 'Rp100.000 Gift Card', points: 100, category: 'gift-card' },
        // Add more card data as needed
    ];

    let currentCategory = 'all';
    let currentSort = null;

    function buildCard(cardData) {
        const card = document.createElement('div');
        card.classList.add('col-12', 'col-sm-6', 'col-lg-3', 'mb-4');
        card.innerHTML = `
            <div class="card">
                <div class="card-body">
                    <h3>${cardData.title}</h3>
                    <p>${cardData.description}</p>
                </div>
                <div class="card-img">
                    <img src="${cardData.image}" alt="${cardData.title}">
                </div>
                <div class="card-body">
                    <h4><strong>${cardData.points} Points</strong></h4>
                    <button class="btn redeem-btn" data-id="${cardData.id}" data-points="${cardData.points}" data-description="${cardData.description}">Redeem</button>
                </div>
            </div>
        `;
        return card;
    }

    function renderCards(cards) {
        cardsContainer.innerHTML = '';
        cards.forEach(data => {
            const card = buildCard(data);
            cardsContainer.appendChild(card);
        });
        attachRedeemButtonListeners();
    }

    function filterCards(category) {
        currentCategory = category;
        let filteredCards;
        if (category === 'all') {
            filteredCards = cardData;
        } else {
            filteredCards = cardData.filter(card => card.category === category);
        }
        if (currentSort) {
            filteredCards = sortCards(filteredCards, currentSort);
        }
        renderCards(filteredCards);
    }

    function sortCards(cards, sortType) {
        if (sortType === 'lowest') {
            return cards.sort((a, b) => a.points - b.points);
        } else if (sortType === 'highest') {
            return cards.sort((a, b) => b.points - a.points);
        }
        return cards;
    }

    filterButtons.forEach(button => {
        button.addEventListener('click', () => {
            filterButtons.forEach(btn => btn.classList.remove('active'));
            button.classList.add('active');
            const category = button.getAttribute('data-category');
            filterCards(category);
        });
    });

    sortButtons.forEach(button => {
        button.addEventListener('click', () => {
            sortButtons.forEach(btn => btn.classList.remove('active'));
            button.classList.add('active');
            const sortType = button.getAttribute('data-sort');
            currentSort = sortType;
            const filteredCards = cardData.filter(card => currentCategory === 'all' || card.category === currentCategory);
            const sortedCards = sortCards(filteredCards, sortType);
            renderCards(sortedCards);
        });
    });

    function attachRedeemButtonListeners() {
        const redeemButtons = document.querySelectorAll('.redeem-btn');
        redeemButtons.forEach(button => {
            button.addEventListener('click', () => {
                const points = parseInt(button.getAttribute('data-points'));
                const description = button.getAttribute('data-description');
                
                // Check if user is logged in (example using localStorage)
                const userEmail = localStorage.getItem('userEmail');
                if (!userEmail) {
                    showEmailModal();
                    return;
                }

                // Fetch user profile to get current points and subtract points
                fetchPointsSubtraction(userEmail, points, description);
            });
        });
    }

    function fetchPointsSubtraction(userEmail, points, description) {
        fetch(`http://localhost:911/Points/SubtractPoints`, {
            method: 'POST', // Assuming this endpoint supports POST
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                emailOrId: userEmail,
                points: points
            })
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            if (data === "Points successfully subtracted") {
                showConfirmationModal(description);
                // Update user points display
                userPointsElement.textContent = data.userProfile.points;
            } else {
                showInsufficientPointsModal();
            }
        })
        .catch(error => {
            console.error('Error redeeming item:', error);
            alert('An error occurred while redeeming the item. Please try again later.');
        });
    }

    function showEmailModal() {
        emailModal.style.display = 'block';
        closeButtons.forEach(button => {
            button.onclick = function() {
                emailModal.style.display = 'none';
            }
        });
    }

    function showConfirmationModal(description) {
        confirmationModal.style.display = 'block';
        const modalContent = confirmationModal.querySelector('.modal-content');
        modalContent.innerHTML = `
            <span class="close">&times;</span>
            <p>Redeem ${description} successfully!</p>
        `;
        closeButtons.forEach(button => {
            button.onclick = function() {
                confirmationModal.style.display = 'none';
            }
        });
    }

    function showInsufficientPointsModal() {
        insufficientPointsModal.style.display = 'block';
        closeButtons.forEach(button => {
            button.onclick = function() {
                insufficientPointsModal.style.display = 'none';
            }
        });
    }

    closeButtons.forEach(button => {
        button.onclick = function() {
            emailModal.style.display = 'none';
            confirmationModal.style.display = 'none';
            insufficientPointsModal.style.display = 'none';
        }
    });

    // Event listener for submit email button
    submitEmailButton.addEventListener('click', function(event) {
        event.preventDefault();
        
        const userEmail = userEmailInput.value.trim();
        if (!validateEmail(userEmail)) {
            alert('Please enter a valid email address.');
            return;
        }

        // Optionally, validate if the email exists in your system

        // Display feedback to the user (example alert)
        alert('Email checked successfully!');

        // Optionally, close the modal or update UI
        emailModal.style.display = 'none';

        // Save the email in localStorage
        localStorage.setItem('userEmail', userEmail);
    });

    // Utility function to validate email format
    function validateEmail(email) {
        const re = /\S+@\S+\.\S+/;
        return re.test(email);
    }

    // Initialize the page
    renderCards(cardData);
});
