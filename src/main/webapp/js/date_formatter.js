(function dateFormatter() {
    console.log('called');
    document.querySelectorAll('.date-to-format').forEach(item => {
        const timestamp = item.textContent;
        const date = new Date(Number(timestamp));

        item.textContent = date.toLocaleDateString();
    });
})();