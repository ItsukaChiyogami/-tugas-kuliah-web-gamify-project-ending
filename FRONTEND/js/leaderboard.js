$(document).ready(function() {
    let currentUserEmail = localStorage.getItem('email');

    function fetchLeaderboard() {
        $.ajax({
            url: "http://localhost:911/Leaderboard/Top",
            method: "GET",
            success: function(data) {
                updateLeaderboard(data, currentUserEmail);
            },
            error: function(error) {
                console.error("Error fetching leaderboard data", error);
            }
        });
    }

    function fetchCurrentUser() {
        $.ajax({
            url: `http://localhost:911/Leaderboard/User?email=${currentUserEmail}`,
            method: "GET",
            success: function(data) {
                updateUserRanking(data);
                // Store email in local storage
                localStorage.setItem('email', data.user.email);
                currentUserEmail = data.user.email; // Update current user email
            },
            error: function(error) {
                console.error("Error fetching user data", error);
            }
        });
    }

    function updateLeaderboard(players, currentUserEmail) {
        const leaderboardList = $("#leaderboard-list");
        leaderboardList.empty();

        players.forEach((player, index) => {
            const listItem = $(`<li><span class="rank">${index + 1}.</span> ${player.user.username} - ${player.user.points} points</li>`);
            if (player.user.email === currentUserEmail) {
                listItem.addClass('highlight');
            }
            leaderboardList.append(listItem);
        });
    }

    function updateUserRanking(user) {
        const userRankingElement = $("#user-ranking");
        if (user) {
            userRankingElement.text(`Your ranking: ${user.user.username} - ${user.user.points} points`);
        } else {
            userRankingElement.text("User not found in leaderboard");
        }
    }

    fetchLeaderboard();
    fetchCurrentUser();
});
