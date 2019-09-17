/**
 * Kasboek rubrieken aanpassen
 */

/**
 * 
 * 
 * Nieuwe rubrieken
 * 
 * 
 */

"use strict";

let rubriekModal;
let rubriekModalAchtergrond;
let rubriekModalForm;


function newRubriek() {
	console.log("New rubriek");
	let modal = document.querySelector('#rubriekModal');
	if(modal == null){
		let data = load_HTML('/rubriek/editRubriek')
		.then((data) => {
			document.getElementById("editRubriekModalHolder").innerHTML = data;
			setup_newRubriekModal();
		})
		.catch((error) => {
			console.log('FOUT: ' + error);
		});
	} else {
		setup_newRubriekModal();
	};
};

function setup_newRubriekModal() {
	rubriekModalAchtergrond = document.querySelector('#rubriekModalAchtergrond');
	rubriekModal = document.querySelector('#rubriekModal');
	rubriekModalForm = document.getElementById('editRubriekModalForm');
	document.getElementById('modal-titel').innerHTML = 'Nieuwe rubriek';
	document.getElementById('modal-titel').classList.remove('text-danger');
	document.getElementById('form_body_error').style.display = 'none';
	document.querySelector('#rubriek').focus();
	document.getElementById('rubriek').readOnly = false;
	window.onkeyup = function (event) {
		if(event.keyCode == 27) {
			listener_newRubriek_close(event);
		}
	};
	showNewRubriekModal(true);
};

function listener_newRubriek_close(event) {
	event.preventDefault;
	showNewRubriekModal(false);
};

function listener_newRubriek_submit(event) {
	event.preventDefault();
	let formData = new FormData(document.getElementById('editRubriekModalForm'));
	let data = post_Form('/rubriek/save_newRubriek', formData)
	.then((data) => {
		showNewRubriekModal(false);
		rubriek_tabel_laden(data);
	})
	.catch((error) => {
		console.log('FOUT: ' + error);
	});
	return false;
};

function showNewRubriekModal(show) {
	if(show) {
		document.getElementById('editRubriekModalForm').addEventListener('submit', listener_newRubriek_submit, false);
		document.querySelector('.closeBtnX').addEventListener('click', listener_newRubriek_close, false);
		document.getElementById('sluiten').addEventListener('click', listener_newRubriek_close, false);
		rubriekModalAchtergrond.addEventListener('click', listener_newRubriek_close, false);
		rubriekModalAchtergrond.classList.add('show');
		rubriekModal.classList.add('on');
		rubriekModalAchtergrond.classList.remove('hide');
		rubriekModal.classList.remove('off');
	} else {
		document.getElementById('editRubriekModalForm').reset();
		document.getElementById('editRubriekModalForm').removeEventListener('submit', listener_newRubriek_submit, false);
		document.querySelector('.closeBtnX').removeEventListener('click', listener_newRubriek_close, false);
		document.getElementById('sluiten').removeEventListener('click', listener_newRubriek_close, false);
		rubriekModalAchtergrond.removeEventListener('click', listener_newRubriek_close, false);
		rubriekModalAchtergrond.classList.add('hide');
		rubriekModal.classList.add('off');
		rubriekModalAchtergrond.classList.remove('show');
		rubriekModal.classList.remove('on');
	}
};

/**
 * 
 * 
 * Update rubrieken
 * 
 * 
 */

function updateRubriek() {
	console.log("Update rubriek");
	let modal = document.querySelector('#rubriekModal');
	if(modal == null){
		let data = load_HTML('/rubriek/editRubriek')
		.then((data) => {
			document.getElementById("editRubriekModalHolder").innerHTML = data;
			setup_updateRubriekModal();
		})
		.catch((error) => {
			console.log('FOUT: ' + error);
		});
	} else {
		setup_updateRubriekModal();
	};
};

function setup_updateRubriekModal() {
	console.log("Setup Update rubriek");
	rubriekModalAchtergrond = document.querySelector('#rubriekModalAchtergrond');
	rubriekModal = document.querySelector('#rubriekModal');
	rubriekModalForm = document.getElementById('editRubriekModalForm');
	let actief_row = document.querySelector('tr.active');
	if (actief_row === undefined || actief_row === null) {
		document.getElementById('modal-titel').innerHTML = 'Je hebt geen selectie gemaakt!';
		document.getElementById('modal-titel').classList.add('text-danger');
		document.getElementById('editRubriek_save').style.visibility = 'hidden';
		document.getElementById('rubriek').readOnly = true;
	} else {
		document.getElementById('modal-titel').innerHTML = 'Update rubriek!';
		document.getElementById('modal-titel').classList.remove('text-danger');
		document.getElementById('editRubriek_save').style.visibility = 'visible';
		document.getElementById('id').value = Rubriek_gegevens.id;
		document.getElementById('rubriek').readOnly = false;
		document.getElementById('rubriek').value = Rubriek_gegevens.rubriek;
	};
	document.getElementById('form_body_error').style.display = 'none';
	document.getElementById('form_body').style.visibility = 'visible';
	document.querySelector('#rubriek').focus();
	window.onkeyup = function (event) {
		if(event.keyCode == 27) {
			listener_updateRubriek_close(event);
		}
	};
	showUpdateRubriekModal(true);
};

function listener_updateRubriek_close(event) {
	event.preventDefault();
	showUpdateRubriekModal(false);
};

function listener_updateRubriek_submit(event) {
	event.preventDefault();
	let formData = new FormData(document.getElementById('editRubriekModalForm'));
	let data = put_Form('/rubriek/save_updateRubriek', formData)
	.then((data) => {
		showUpdateRubriekModal(false);
		rubriek_tabel_laden(data);
	})
	.catch((error) => {
		console.error('FOUT: ' + error);
	});
};

function showUpdateRubriekModal(show) {
	if(show) {
		document.getElementById('editRubriekModalForm').addEventListener('submit', listener_updateRubriek_submit, false);
		document.querySelector('.closeBtnX').addEventListener('click', listener_updateRubriek_close, false);
		document.getElementById('sluiten').addEventListener('click', listener_updateRubriek_close, false);
		rubriekModalAchtergrond.addEventListener('click', listener_updateRubriek_close, false);
		rubriekModalAchtergrond.classList.add('show');
		rubriekModal.classList.add('on');
		rubriekModalAchtergrond.classList.remove('hide');
		rubriekModal.classList.remove('off');
	} else {
		document.getElementById('editRubriekModalForm').reset();
		document.getElementById('editRubriekModalForm').removeEventListener('submit', listener_updateRubriek_submit, false);
		document.querySelector('.closeBtnX').removeEventListener('click', listener_updateRubriek_close, false);
		document.getElementById('sluiten').removeEventListener('click', listener_updateRubriek_close, false);
		rubriekModalAchtergrond.removeEventListener('click', listener_updateRubriek_close, false);
		rubriekModalAchtergrond.classList.add('hide');
		rubriekModal.classList.add('off');
		rubriekModalAchtergrond.classList.remove('show');
		rubriekModal.classList.remove('on');
	}
};

/**
 * 
 * 
 * Delete rubrieken
 * 
 * 
 */

function deleteRubriek() {
	console.log("Delete rubriek");
	let modal = document.querySelector('#rubriekModal');
	if(modal == null){
		let data = load_HTML('/rubriek/editRubriek')
		.then((data) => {
			document.getElementById("editRubriekModalHolder").innerHTML = data;
			setup_deleteRubriekModal();
		})
		.catch((error) => {
			console.log('FOUT: ' + error);
		});
	} else {
		setup_deleteRubriekModal();
	};
};

async function setup_deleteRubriekModal() {
	rubriekModalAchtergrond = document.querySelector('#rubriekModalAchtergrond');
	rubriekModal = document.querySelector('#rubriekModal');
	rubriekModalForm = document.getElementById('editRubriekModalForm');
	document.getElementById('form_body_error').style.display = 'none';
	let actief_row = document.querySelector('tr.active');
	if (actief_row === undefined || actief_row === null) {
		document.getElementById('modal-titel').innerHTML = 'Je hebt geen selectie gemaakt!';
		document.getElementById('modal-titel').classList.add('text-danger');
		document.getElementById('editRubriek_save').style.visibility = 'hidden';
		document.getElementById('rubriek').readOnly = true;
	} else {
		let exist = await existRecord('/rubriek/existKasboekByRubriekId/' + Rubriek_gegevens.id);
		if(exist == 'true') {
			document.getElementById('editRubriek_save').style.visibility = 'hidden';
			document.getElementById('form_body_error').style.display = 'block';
			document.getElementById('form_body').style.visibility = 'hidden';
			document.getElementById('form_body_error_naam').innerHTML = Rubriek_gegevens.rubriek;
		}else {
			document.getElementById('editRubriek_save').style.visibility = 'visible';
			document.getElementById('form_body_error').style.display = 'none';
			document.getElementById('form_body').style.visibility = 'visible';
		};
		document.getElementById('modal-titel').innerHTML = 'Delete rubriek!';	
		document.getElementById('editRubriek_save').innerHTML = 'Delete rubriek';
		document.getElementById('id').value = Rubriek_gegevens.id;
		document.getElementById('rubriek').value = Rubriek_gegevens.rubriek;
		document.getElementById('rubriek').readOnly = true;
	};
	document.getElementById('modal-titel').classList.add('text-danger');
	document.querySelector('#rubriek').focus();
	window.onkeyup = function (event) {
		if(event.keyCode == 27) {
			listener_updateRubriek_close(event);
		}
	};
	showDeleteRubriekModal(true);
};

function listener_deleteRubriek_close(event) {
	event.preventDefault();
	showDeleteRubriekModal(false);
};

function listener_deleteRubriek_submit(event) {
	event.preventDefault();
	let formData = new FormData(document.getElementById('editRubriekModalForm'));
	for (var pair of formData.entries()) {
	    console.log(pair[0]+ ', ' + pair[1]); 
	}
	let data = delete_Form('/rubriek/save_deleteRubriek', formData)
	.then((data) => {
		showDeleteRubriekModal(false);
		rubriek_tabel_laden(data);
	})
	.catch((error) => {
		console.error('FOUT: ' + error);
	});
};

function showDeleteRubriekModal(show) {
	if(show) {
		document.getElementById('editRubriekModalForm').addEventListener('submit', listener_deleteRubriek_submit, false);
		document.querySelector('.closeBtnX').addEventListener('click', listener_deleteRubriek_close, false);
		document.getElementById('sluiten').addEventListener('click', listener_deleteRubriek_close, false);
		rubriekModalAchtergrond.addEventListener('click', listener_deleteRubriek_close, false);
		rubriekModalAchtergrond.classList.add('show');
		rubriekModal.classList.add('on');
		rubriekModalAchtergrond.classList.remove('hide');
		rubriekModal.classList.remove('off');
	} else {
		document.getElementById('editRubriekModalForm').reset();
		document.getElementById('editRubriekModalForm').removeEventListener('submit', listener_deleteRubriek_submit, false);
		document.querySelector('.closeBtnX').removeEventListener('click', listener_deleteRubriek_close, false);
		document.getElementById('sluiten').removeEventListener('click', listener_deleteRubriek_close, false);
		rubriekModalAchtergrond.removeEventListener('click', listener_deleteRubriek_close, false);
		rubriekModalAchtergrond.classList.add('hide');
		rubriekModal.classList.add('off');
		rubriekModalAchtergrond.classList.remove('show');
		rubriekModal.classList.remove('on');
	}
};
