function makeLayer(blank, display = 'flex', ...args) {
  const blankLayer = document.getElementById(blank);
  if (!blankLayer) return 0;

  const handler = (e) => {
    e.preventDefault(); // предотвращает двойное срабатывание
    if (blankLayer.style.display === 'none') {
      blankLayer.style.display = display;
      blankLayer.style.zIndex = 10;
    } else {
      document.getElementById('name').value = '';
      document.getElementById('des').value = '';
      document.getElementById('file-msg-icon').innerHTML = '';
      document.getElementById('create-blank-h').innerHTML = 'Создать объявление';
      document.getElementById('img_area').style.backgroundColor = '#00000000';
      document.getElementById('item-name').numberId = '';
      document.getElementById('CreateButton').style.display = 'block';
      document.getElementById('UpdateButton').style.display = 'none';
      document.getElementById('DeleteButton').style.display = 'none';
      blankLayer.style.display = 'none';
      blankLayer.style.zIndex = -999;
    }
  };

  for (let btn of args) {
    const button = document.getElementById(btn);
    if (button) {
      // Удаляем старые обработчики (если нужно)
      button.addEventListener('click', handler, { passive: false });
      button.addEventListener('touchstart', handler, { passive: false });
    }
  }
}
