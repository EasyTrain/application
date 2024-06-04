document.addEventListener('DOMContentLoaded', (event) => {
    const timeSelect = document.getElementById('time-select');
    const now = new Date();
    const timeOptions = [];

    let result = document.getElementById("selectedTime");

    for (let i = 0; i < 24; i++) {
        const optionTime = new Date(now.getTime() + i * 60 * 60 * 1000);
        const hours = String(optionTime.getHours()).padStart(2, '0');
        //const minutes = String(optionTime.getMinutes()).padStart(2, '0');
        const timeString = `${hours}:00`;
        timeOptions.push(timeString);
    }

    timeOptions.forEach(time => {
        const optionElement = document.createElement('option');
        optionElement.value = time;
        optionElement.textContent = time;
        timeSelect.appendChild(optionElement);
    });

    // The placeholder remains as the default value until a selection is made
    timeSelect.value = "";
    result.innerHTML = timeSelect.value;
});