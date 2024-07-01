
        document.addEventListener('DOMContentLoaded', function () {
            var saveUserBtn = document.getElementById('saveUserBtn');
            var positionSelect = document.getElementById('position');
            var branchSelect = document.getElementById('branch'); // Thêm đối tượng chi nhánh

            // Get JWT token from localStorage
            var jwtToken = localStorage.getItem('jwtToken');

            // Load list of positions
            fetch('http://localhost:8080/api/admin/position_getAll', {
                headers: {
                    'Authorization': 'Bearer ' + jwtToken,
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                    console.log('List of positions:', data);
                    // Clear previous options
                    positionSelect.innerHTML = '';

                    // Populate position options
                    data.forEach(position => {
                        var option = document.createElement('option');
                        option.value = position.id;
                        option.textContent = position.name;
                        positionSelect.appendChild(option);
                    });
                })
                .catch(error => {
                    console.error('Error fetching positions:', error);
                    // Handle error: display message to user or retry logic
                });

            // Load list of branches
            fetch('http://localhost:8080/api/admin/branch_getAll', {
                headers: {
                    'Authorization': 'Bearer ' + jwtToken,
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                    console.log('List of branches:', data);
                    // Clear previous options
                    branchSelect.innerHTML = '';

                    // Populate branch options
                    data.forEach(branch => {
                        var option = document.createElement('option');
                        option.value = branch.id;
                        option.textContent = branch.name;
                        branchSelect.appendChild(option);
                    });
                })
                .catch(error => {
                    console.error('Error fetching branches:', error);
                    // Handle error: display message to user or retry logic
                });

            // Event listener for Save User button
            saveUserBtn.addEventListener('click', function () {
                var form = document.getElementById('userForm');
                var formData = new FormData(form);

                fetch('http://localhost:8080/api/admin/user_add', {
                    method: 'POST',
                    headers: {
                        'Authorization': 'Bearer ' + jwtToken,
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(Object.fromEntries(formData))
                })
                    .then(response => response.json())
                    .then(data => {
                        console.log('Success:', data);
                        // Optional: Update table or show success message
                    })
                    .catch(error => {
                        console.error('Error saving user:', error);
                        // Handle error: display message to user or retry logic
                    });
            });
        });
