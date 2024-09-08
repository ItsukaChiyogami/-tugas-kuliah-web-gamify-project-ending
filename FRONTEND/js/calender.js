document.addEventListener('DOMContentLoaded', function () {
    const monthNames = ["Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"];
    let currentMonth = new Date().getMonth();
    let currentYear = new Date().getFullYear();

    function updateCalendar() {
        document.getElementById('monthButton').innerText = `${monthNames[currentMonth]} ${currentYear}`;
        const calendarElement = document.getElementById('calendar');
        calendarElement.innerHTML = createCalendarHTML(currentMonth, currentYear);
    }

    function createCalendarHTML(month, year) {
        const firstDay = new Date(year, month, 1).getDay();
        const daysInMonth = new Date(year, month + 1, 0).getDate();
        const today = new Date();
        const isCurrentMonth = (year === today.getFullYear() && month === today.getMonth());

        let calendarHTML = '<table><thead><tr><th>Su</th><th>Mo</th><th>Tu</th><th>We</th><th>Th</th><th>Fr</th><th>Sa</th></tr></thead><tbody><tr>';
        let day = 1;

        for (let i = 0; i < 7; i++) {
            if (i < firstDay) {
                calendarHTML += '<td></td>';
            } else {
                calendarHTML += `<td${isCurrentMonth && day === today.getDate() ? ' class="today"' : ''}>${day}</td>`;
                day++;
            }
        }
        calendarHTML += '</tr>';

        while (day <= daysInMonth) {
            calendarHTML += '<tr>';
            for (let i = 0; i < 7; i++) {
                if (day <= daysInMonth) {
                    calendarHTML += `<td${isCurrentMonth && day === today.getDate() ? ' class="today"' : ''}>${day}</td>`;
                } else {
                    calendarHTML += '<td></td>';
                }
                day++;
            }
            calendarHTML += '</tr>';
        }
        calendarHTML += '</tbody></table>';
        return calendarHTML;
    }

    document.getElementById('prevMonth').addEventListener('click', () => {
        currentMonth = (currentMonth === 0) ? 11 : currentMonth - 1;
        if (currentMonth === 11) {
            currentYear--;
        }
        updateCalendar();
    });

    document.getElementById('nextMonth').addEventListener('click', () => {
        currentMonth = (currentMonth === 11) ? 0 : currentMonth + 1;
        if (currentMonth === 0) {
            currentYear++;
        }
        updateCalendar();
    });

    updateCalendar();
});
