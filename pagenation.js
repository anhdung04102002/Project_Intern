document.addEventListener('DOMContentLoaded', function () {
    var jwtToken = localStorage.getItem('jwtToken');
    var paginationContainer = document.getElementById('pagination');

    // Function to fetch users with pagination
    function fetchUsersWithPagination(page, size) {
        fetch(`http://localhost:8080/api/admin/user_pagination/${page}/${size}`, {
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
                console.log('Paginated users:', data);
                // Clear previous table rows
                var userTableBody = document.getElementById('userTableBody');
                userTableBody.innerHTML = '';

                // Populate user data into table
                data.response.forEach(user => {
                    var positionName = user.position ? user.position.name : 'N/A';
                    var branchName = user.branch ? user.branch.name : 'N/A';
                    var row = `
            <tr>
              <td>${user.name}</td>
              <td>${user.email}</td>
              <td>${user.status ? 'Active' : 'Inactive'}</td>
              <td>${user.userType}</td>
              <td>${user.salary}</td>
              <td>${user.salaryDate}</td>
              <td>${user.level}</td>
              <td>${user.sex ? 'Male' : 'Female'}</td>
              <td>${positionName}</td>
              <td>${branchName}</td>
                <td>
                            <!-- Dropdown button -->
                            <div class="dropdown">
                                <button class="btn btn-secondary dropdown" type="button" data-toggle="dropdown">
                                    Actions
                                </button>
                                <div class="dropdown-menu">
                                    <a class="dropdown-item" href="#">Edit</a>
                                    <a class="dropdown-item" href="#">Delete</a>
                                    <!-- Add more actions as needed -->
                                </div>
                            </div>
                        </td>


            </tr>`;
                    userTableBody.insertAdjacentHTML('beforeend', row);
                });

                // Update pagination links
                updatePagination(data.recordCount, page, size);
            })
            .catch(error => {
                console.error('Error fetching paginated users:', error);
                // Handle error: display message to user or retry logic
            });
    }

    // Function to update pagination links
    function updatePagination(totalPages, currentPage, size) {
        paginationContainer.innerHTML = '';

        // Previous button
        var previousButton = createPaginationButton('Previous', currentPage - 1, totalPages, size);
        paginationContainer.appendChild(previousButton);

        // Page numbers around current page
        var startPage = Math.max(1, currentPage - 1);
        var endPage = Math.min(totalPages, currentPage + 1);

        for (let i = startPage; i <= endPage; i++) {
            var pageButton = createPaginationButton(i, i, totalPages, size);
            paginationContainer.appendChild(pageButton);
        }

        // Next button
        var nextButton = createPaginationButton('Next', currentPage + 1, totalPages, size);
        if (currentPage >= totalPages) {
            nextButton.classList.add('disabled');
        }
        paginationContainer.appendChild(nextButton);
    }

    // Function to create pagination buttons
    function createPaginationButton(label, page, totalPages, size) {
        var li = document.createElement('li');
        li.className = 'page-item';
        if (label === 'Previous' && page < 1 || label === 'Next' && page > totalPages) {
            li.classList.add('disabled');
        }

        var a = document.createElement('a');
        a.className = 'page-link';
        a.href = '#';
        a.textContent = label;

        if (label !== 'Previous' && label !== 'Next') {
            a.addEventListener('click', function () {
                fetchUsersWithPagination(page, size);
            });
        } else {
            if (label === 'Previous') {
                a.addEventListener('click', function () {
                    fetchUsersWithPagination(page, size);
                });
            } else {
                a.addEventListener('click', function () {
                    fetchUsersWithPagination(page, size);
                });
            }
        }

        li.appendChild(a);
        return li;
    }

    // Initial fetch on page load
    fetchUsersWithPagination(1, 5);
});
function showDropdownMenu() {
    var dropdownMenu = document.getElementById("dropdownMenu");
    if (dropdownMenu.style.display === "none") {
        dropdownMenu.style.display = "block";
    } else {
        dropdownMenu.style.display = "none";
    }
}

