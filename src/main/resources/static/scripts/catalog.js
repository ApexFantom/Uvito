async function FetchCatalog() {
    const catalog = document.getElementById('items-list-align');
    catalog.innerHTML = ''; // Очищаем список перед добавлением новых элементов

    try {
        const response = await fetch('/api/useless-items');
        const data = await response.json();

        data.forEach(item => {
            const div = document.createElement('div');
            div.classList.add('items-unit-wrapper', 'ib', 'va');
            div.id = 'ITEM';

            div.innerHTML = `
                <div class="items-unit-texture" style="background-image: url('${item.imageUrl}');"></div>
                <div class="items-unit-difficulty">${item.name}</div>
                <div class="items-unit-shortdes">${item.description}</div>
                <div class="items-unit-price">${item.author.email}</div>
                <div class="items-unit-dos">${item.author.username}</div>
            `;

            // Добавляем обработчик клика для открытия модального окна
            div.addEventListener('click', () => {
                if (`${item.author.email}` === localStorage.getItem('email')){
                    document.querySelector('#create-blank').style.display = 'flex';
                    document.querySelector('#create-blank').style.zIndex = 10;
                    document.getElementById('name').value = `${item.name}`;
                    document.getElementById('des').value = `${item.description}`;
                    document.getElementById('file-msg-icon').innerHTML = `${item.imageUrl}`;
                    document.getElementById('create-blank-h').innerHTML = 'Редактировать объявление';
                    document.getElementById('img_area').style.backgroundColor = '#af9c587d';
                    document.getElementById('item-name').setAttribute('numberid',`${item.id}`);
                    document.getElementById('CreateButton').style.display = 'none';
                    document.getElementById('UpdateButton').style.display = 'block';
                    document.getElementById('DeleteButton').style.display = 'block';




                } else {
                openModal(item);  // предполагаем, что эта функция уже существует
                }
            });

            catalog.appendChild(div);
        });
    } catch (error) {
        console.error('Ошибка загрузки данных:', error);
    }
}

FetchCatalog();










// Функция для открытия модального окна
function openModal(item) {
    const modal = document.getElementById('item-blank');
    const modalContent = document.getElementById('modal-content');

    // Наполняем содержимое модального окна
    modalContent.innerHTML = `
    <div class="close-btn" id="wrapper-blank-close-btn-item">⨯</div>
        <h2>${item.name}</h2>
        <img src="${item.imageUrl}" alt="${item.name}" style="width: 100%; max-height: 300px; object-fit: cover;">
        <p>${item.description}</p>
        <p><strong>Автор:</strong> ${item.author.username}</p>
    `;

    // Показываем модальное окно
    modal.style.display = 'flex';

    // Закрытие модального окна
    const closeBtn = document.getElementById('wrapper-blank-close-btn-item');
    closeBtn.addEventListener('click', () => {
        modal.style.display = 'none';
    });
}
