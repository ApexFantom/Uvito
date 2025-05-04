for (let btn of args) {
  const button = document.getElementById(btn);
  if (button) {
    const toggleLayer = () => {
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

    button.addEventListener('click', toggleLayer);
    button.addEventListener('touchstart', toggleLayer);
  }
}
