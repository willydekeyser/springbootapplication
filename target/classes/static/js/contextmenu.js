/**
 * Context Menu
 */
let menu;

function showMenu(x, y){
	
	const hoogte = document.body.clientHeight();
	const breedte = document.body.clientWidth();
	const verschily = hoogte - y;
	const verschilx = breedte - x;
	console.log("Document: " + x + " - " + y);
	if((verschily) < 150) {
		y = y - (150 - verschily);
	}
	if((verschilx) < 250) {
		x = x - (250 - verschilx);
	}
    menu.style.left = x + 'px';
    menu.style.top = y + 'px';
    menu.classList.add('show-menu');
}

function hideMenu(){
    menu.classList.remove('show-menu');
}

function onContextMenu(e){
    showMenu(e.pageX, e.pageY);
    document.addEventListener('click', onClick, false);
}

function onClick(e){
    hideMenu();
    document.removeEventListener('click', onClick);
}

