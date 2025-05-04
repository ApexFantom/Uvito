// Создаем модальные окна
makeLayer('reg-blank-login', 'flex', 'LOGIN', 'wrapper-blank-close-btn-login');
makeLayer('reg-blank-register', 'flex', 'REG', 'wrapper-blank-close-btn-reg');
makeLayer('reg-blank-logout', 'flex', 'LOGOUT', 'wrapper-blank-close-btn-logout');
makeLayer('create-blank', 'flex', 'CREATE', 'wrapper-blank-close-btn-create');
makeLayer('delete-blank', 'flex', 'DELETE', 'wrapper-blank-close-btn-delete');


// Функции для управления отображением интерфейса
function showLoggedInUI() {
    document.querySelector('#LOGIN').style.display = 'none';
    document.querySelector('#REG').style.display = 'none';
    document.querySelector('#LOGOUT').style.display = 'flex';
    document.querySelector('#USERNAME').style.display = 'flex';
    document.querySelector('#CREATE').style.display = 'flex';
}

function showLoggedOutUI() {
    document.querySelector('#LOGIN').style.display = 'flex';
    document.querySelector('#REG').style.display = 'flex';
    document.querySelector('#LOGOUT').style.display = 'none';
    document.querySelector('#USERNAME').style.display = 'none';
    document.querySelector('#CREATE').style.display = 'none';
}

// Универсальная функция для работы с fetch-запросами
async function apiRequest(url, method = 'GET', body = null, headers = {}) {
    try {
        const options = { method, headers };

        if (body) {
            options.body = JSON.stringify(body);
            options.headers['Content-Type'] = 'application/json';
        }

        const response = await fetch(url, options);
        if (!response.ok) {
            console.error(`Ошибка запроса: ${response.status} - ${response.statusText}`);
        }
        return response;
    } catch (error) {
        console.error(`Ошибка: ${error.message}`);
        throw error;
    }
}

// Проверка токена авторизации
async function validateToken(token) {
    const response = await apiRequest('/api/auth/validate', 'GET', null, {
        'Authorization': `Bearer ${token}`,
    });
    return response.ok;
}

// Логаут
async function logout() {
    const response = await apiRequest('/api/auth/logout', 'POST', null, {
        'Content-Type': 'application/json',
    });
    if (response.ok) {
        localStorage.removeItem('token');
        localStorage.removeItem('email');
        showLoggedOutUI();
        document.getElementById('reg-blank-logout').style.display = 'none';
    }
}

// Логин
async function login(email, password) {
    const response = await apiRequest('/api/auth/login', 'POST', { email, password });
    if (response.ok) {
        const data = await response.json();
        localStorage.setItem('token', data.token);
        localStorage.setItem('email', email);
        showLoggedInUI();
        return true;
    }
    return false;
}

// Регистрация
async function register(username, email, password) {
    const response = await apiRequest('/api/auth/register', 'POST', { username, email, password });
    if (response.ok) {
        showLoggedInUI();
        return true;
    }
    return false;
}
async function createItem(name, description, imageFile, author) {
    const formData = new FormData();
    formData.append('name', name);
    formData.append('description', description);
    formData.append('image', imageFile);
    formData.append('authorEmail', author);

    const response = await fetch('/api/useless-items', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
        body: formData,
    });

    if (response.ok) {
        return true;
    }
    return false;
}
async function updateItem(name, description, imageFile, author, id) {
    const numberId = id;
    const formData = new FormData();
    formData.append('name', name);
    formData.append('description', description);
    formData.append('image', imageFile);
    formData.append('authorEmail', author);

    const response = await fetch(`/api/useless-items/${numberId}`, {
        method: 'PUT',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
        body: formData,
    });

    if (response.ok) {
        return true;
    }
    return false;
}
async function deleteItem(id) {
    const response = await fetch(`/api/useless-items/${id}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
        }
    });

    if (response.ok) {
        console.log(`Item with id ${id} deleted successfully.`);
        return true;
    } else {
        console.log(`Failed to delete item with id ${id}`);
        return false;
    }
}
















// Основная логика
document.addEventListener('DOMContentLoaded', async () => {
    const token = localStorage.getItem('token');
    const email = localStorage.getItem('email');
    showLoggedOutUI();
    if (token) {
        const isValid = await validateToken(token);
        if (isValid) {
            console.log('Пользователь авторизован');
            showLoggedInUI();
            document.getElementById('USERNAME').textContent = email;
        } else {
            console.log('Токен недействителен');
            localStorage.removeItem('token');
            localStorage.removeItem('email');
            showLoggedOutUI();
            document.getElementById('USERNAME').textContent = '';
        }
    }

    document.querySelector('#LogoutButton').addEventListener('click', async () => {
        await logout();
        document.getElementById('USERNAME').textContent = '';
    });

    document.querySelector('#LoginButton').addEventListener('click', async () => {
        const email = document.querySelector('#emailL').value;
        const password = document.querySelector('#passwordL').value;
        const success = await login(email, password);
        if (success) {
            document.getElementById('reg-blank-login').style.display = 'none';
            document.getElementById('USERNAME').textContent = email;
        } else {
        document.getElementById('reg-blank-login').style.display = 'none';
        }
    });

    document.querySelector('#RegisterButton').addEventListener('click', async () => {
        const username = document.querySelector('#usernameR').value;
        const email = document.querySelector('#emailR').value;
        const password = document.querySelector('#passwordR').value;
        const success = await register(username, email, password);
        if (success) {
            await login(email, password)
            document.getElementById('reg-blank-register').style.display = 'none';
            document.getElementById('USERNAME').textContent = email;
        } else {
                 document.getElementById('reg-blank-login').style.display = 'none';
                 }
    });
    document.getElementById('CreateButton').addEventListener('click', async () => {
        const name = document.querySelector('#name').value;
        const description = document.querySelector('#des').value;
        const image = document.querySelector('#img').files[0];
        const author = localStorage.getItem('email');
        const success = await createItem(name, description, image, author);
        if (success) {
            document.getElementById('create-blank').style.display = 'none';
            FetchCatalog();
        }
    });
    document.getElementById('UpdateButton').addEventListener('click', async () => {
            const name = document.querySelector('#name').value;
            const numberId = document.querySelector('#item-name').getAttribute('numberid');
            const description = document.querySelector('#des').value;
            const image = document.querySelector('#img').files[0];
            const author = localStorage.getItem('email');
            const success = await updateItem(name, description, image, author, numberId);
            if (success) {
                document.getElementById('create-blank').style.display = 'none';
                document.getElementById('CreateButton').style.display = 'block';
                document.getElementById('UpdateButton').style.display = 'none';
                document.getElementById('DeleteButton').style.display = 'none';
                FetchCatalog();
            }
        });
        document.getElementById('DeleteButton').addEventListener('click', async () => {
                    const numberId = document.querySelector('#item-name').getAttribute('numberid');
                    const success = await deleteItem(numberId);
                    if (success) {
                        document.getElementById('create-blank').style.display = 'none';
                        document.getElementById('delete-blank').style.display = 'none';
                        FetchCatalog();
                    }
                });

    });
