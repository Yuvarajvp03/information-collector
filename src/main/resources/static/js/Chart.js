<script>
    document.addEventListener("DOMContentLoaded", function() {
        const ctx = document.getElementById('submissionChart').getContext('2d');
        
        // Pass data using Thymeleaf
        const submittedCount = [[${submittedCount}]];
        const notSubmittedCount = [[${notSubmittedCount}]];
        
        console.log("Submitted Count:", submittedCount);
        console.log("Not Submitted Count:", notSubmittedCount);

        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ['Submitted', 'Not Submitted'],
                datasets: [{
                    label: 'Number of Students',
                    data: [submittedCount, notSubmittedCount],
                    backgroundColor: [
                        'rgba(75, 192, 192, 0.6)',
                        'rgba(255, 99, 132, 0.6)'
                    ],
                    borderColor: [
                        'rgba(75, 192, 192, 1)',
                        'rgba(255, 99, 132, 1)'
                    ],
                    borderWidth: 1,
                    barThickness: 30
                }]
            },
            options: {
                scales: {
                    x: {
                        beginAtZero: true,
                        title: {
                            display: true,
                            text: 'Submission Status'
                        }
                    },
                    y: {
                        beginAtZero: true,
                        title: {
                            display: true,
                            text: 'Number of Students'
                        }
                    }
                },
                responsive: true,
                maintainAspectRatio: false
            }
        });
    });
</script>
