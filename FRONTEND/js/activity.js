$(document).ready(function() {
    let currentUserEmail = localStorage.getItem('email');
    let questsData = []; // Variable to store quests data

    // Function to fetch leaderboard data
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

    // Function to fetch current user data
    function fetchCurrentUser() {
        $.ajax({
            url: `http://localhost:911/Leaderboard/User?email=${currentUserEmail}`,
            method: "GET",
            success: function(data) {
                updateUserRanking(data);
                localStorage.setItem('email', data.user.email);
                currentUserEmail = data.user.email;
            },
            error: function(error) {
                console.error("Error fetching user data", error);
            }
        });
    }

    // Function to fetch quests data
    function fetchQuests() {
        $.ajax({
            url: "http://localhost:911/quests",
            method: "GET",
            success: function(data) {
                questsData = data; // Store quests data
                updateQuestList(data);
                initializeCalendar(data);
                populateCategoryFilter(data); // Populate category filter dropdown
            },
            error: function(error) {
                console.error("Error fetching quests", error);
                $("#noMission").show();
            }
        });
    }

    // Function to update leaderboard list
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

    // Function to update user ranking
    function updateUserRanking(user) {
        const userRankingElement = $("#user-ranking");
        if (user) {
            userRankingElement.text(`Your ranking: ${user.user.username} - ${user.user.points} points`);
        } else {
            userRankingElement.text("User not found in leaderboard");
        }
    }

    // Function to update quest list
    function updateQuestList(quests) {
        const taskList = $("#task-list");
        taskList.empty();

        if (quests.length === 0) {
            $("#noMission").show();
        } else {
            $("#noMission").hide();

            quests.forEach((quest, index) => {
                const deadline = new Date(quest.deadline);
                const today = new Date();
                const isOverdue = deadline < today;
                const listItem = $(`
                            <div class="task-item ${isOverdue ? 'overdue-task' : ''}">
                                    <div class="task-content">
                                        <h3>${quest.title}</h3>
                                        <p><strong>Deskripsi:</strong> ${quest.description}</p>
                                    </div>
                                    <div class="input-container">
                                        <input type="text" placeholder="Masukkan Link">
                                        <button onclick="verifyTask(this, '${quest._id}')">Verifikasi</button>
                                    </div>
                                </div>
                `);
                taskList.append(listItem);
            });
        }
    }



    fetchLeaderboard();
    fetchCurrentUser();
    fetchQuests();
});

const totalTaskPoints = 100;
let progress = 0;

function verifyTask(button, taskId) {
    const taskItem = $(button).closest('.task-item');
    const inputContainer = taskItem.find('.input-container');
    const inputField = inputContainer.find('input').val().trim();

    if (inputField === '') {
        alert('Please enter a valid link.');
        return;
    }

    // Simulate verification process (replace with actual logic)
    setTimeout(function() {
        inputContainer.hide();

        const taskDone = $('<span class="task-done">DONE <i class="bi bi-check-lg"></i></span>');
        taskItem.append(taskDone);

        const totalTasks = $('.task-item').length;
        const completedTasks = $('.task-done').length;

        progress = (completedTasks / totalTasks) * 100;
        $('.progress').width(progress + '%');
        $('.percent').text('Progress: ' + progress.toFixed(0) + '%');

        if (progress >= 100) {
            $('#claimButton').show();
        }

        // Here you can trigger any API calls or backend operations for verification

        // Example: Claim points after all tasks are verified
        if (progress === 100) {
            $('#claimButton').show();
        }

    }, 1000); // Simulate delay for verification process
}

function addPoints(points) {
    const email = localStorage.getItem('email');
    $.ajax({
        url: `http://localhost:911/Points/AddPoints`,
        method: "POST",
        data: { emailOrId: email, points: points },
        success: function() {
            updateUserPoints();
            $('#claimModal').show();
        },
        error: function(error) {
            console.error("Error adding points", error);
        }
    });
}

function updateUserPoints() {
    const email = localStorage.getItem('email');
    $.ajax({
        url: `http://localhost:911/Leaderboard/User?email=${email}`,
        method: "GET",
        success: function(data) {
            $('#userPoints').text(data.user.points);
        },
        error: function(error) {
            console.error("Error fetching user points", error);
        }
    });
}

function claimPoints() {
    addPoints(totalTaskPoints);
    $('.content-container, .progress-bar, .percent, .task-list, .task-points, .button-container').hide();
    $('#noMission').show();
}

function closeModal(modalId) {
    $(`#${modalId}`).hide();
}


