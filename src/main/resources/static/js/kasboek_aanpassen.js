/**
 * Kasboep aanpassen.
 */
/**
 * 
 * 
 * New kasboek
 * 
 * 
 */

"use strict";

let kasboekModal;
let kasboekModalAchtergrond;
let kasboekModalForm;


function newKasboek() {
	console.log("New kasboek");
	const modal = document.querySelector('#kasboekModal');
	if(modal == null){
		let data = load_HTML('/kasboek/editKasboek')
		.then((data) => {
			document.getElementById("editKasboekModalHolder").innerHTML = data;
			setup_newKasboekModal();
		})
		.catch((error) => {
			console.error('FOUT: ' + error);
		});
	} else {
		setup_newKasboekModal();
	};
};

function setup_newKasboekModal() {
	change_jaar = false;
	change_rubriek = false;
	kasboekModalAchtergrond = document.querySelector('#kasboekModalAchtergrond');
	kasboekModal = document.querySelector('#kasboekModal');
	kasboekModalForm = document.getElementById('editKasboekModalForm');
	document.getElementById('modal-titel').innerHTML = 'Nieuwe kasboek';
	document.getElementById('modal-titel').classList.remove('text-danger');
	document.getElementById('form_body').style.visibility = 'visible';
	document.getElementById('editKasboek_save').style.visibility = 'visible';
	document.getElementById('editKasboek_save').innerHTML = 'Save kasboek';
	document.getElementById('jaartal').addEventListener('input', listener_change_jaar);
	document.getElementById('rubriek').addEventListener('input', listener_change_rubriek);
	document.getElementById('jaartal').focus();
	if (selectedJaar != 0) {
		document.getElementById('jaartal').value = selectedJaar;
		document.getElementById('rubriek').focus();
	};
	if (selectedRubriek != 0) {
		document.getElementById('rubriek').value = selectedRubriek;
		document.getElementById('omschrijving').focus();
	};
	window.onkeyup = function (event) {
		if(event.keyCode == 27) {
			listener_newKasboek_close(event);
		}
	};
	showNewKasboekModal(true);
};

function listener_newKasboek_close(event) {
	event.preventDefault;
	showNewKasboekModal(false);
};

function listener_newKasboek_submit() {
	event.preventDefault();
	const formData = new FormData(document.getElementById('editKasboekModalForm'));
	const data = post_Form('/kasboek/save_newKasboek/' + '2019' + '/' + '1', formData)
	.then((data) => {
		showNewKasboekModal(false);
		kasboek_tabel_refrech(data)
	})
	.catch((error) => {
		console.error('FOUT: ' + error);
	});
};

function listener_change_jaar() {
	change_jaar = true;
};

function listener_change_rubriek() {
	change_rubriek = true;
};

function showNewKasboekModal(show) {
	if(show) {
		document.getElementById('editKasboekModalForm').addEventListener('submit', listener_newKasboek_submit, false);
		document.querySelector('.closeBtnX').addEventListener('click', listener_newKasboek_close, false);
		document.getElementById('sluiten').addEventListener('click', listener_newKasboek_close, false);
		kasboekModalAchtergrond.addEventListener('click', listener_newKasboek_close, false);
		kasboekModalAchtergrond.classList.add('show');
		kasboekModal.classList.add('on');
		kasboekModalAchtergrond.classList.remove('hide');
		kasboekModal.classList.remove('off');
	} else {
		document.getElementById('editKasboekModalForm').reset();
		document.getElementById('editKasboekModalForm').removeEventListener('submit', listener_newKasboek_submit, false);
		document.querySelector('.closeBtnX').removeEventListener('click', listener_newKasboek_close, false);
		document.getElementById('sluiten').removeEventListener('click', listener_newKasboek_close, false);
		kasboekModalAchtergrond.removeEventListener('click', listener_newKasboek_close, false);
		kasboekModalAchtergrond.classList.add('hide');
		kasboekModal.classList.add('off');
		kasboekModalAchtergrond.classList.remove('show');
		kasboekModal.classList.remove('on');
	}
};

/**
 * 
 * 
 * Update kasboek
 * 
 * 
 */

function updateKasboek() {
	console.log("Edit kasboek");
	const modal = document.querySelector('#kasboekModal');
	if(modal == null){
		let data = load_HTML('/kasboek/editKasboek')
		.then((data) => {
			document.getElementById("editKasboekModalHolder").innerHTML = data;
			setup_updateKasboekModal();
		})
		.catch((error) => {
			console.error('FOUT: ' + error);
		});
	} else {
		setup_updateKasboekModal();
	};
};

function setup_updateKasboekModal() {
	kasboekModalAchtergrond = document.querySelector('#kasboekModalAchtergrond');
	kasboekModal = document.querySelector('#kasboekModal');
	kasboekModalForm = document.getElementById('editKasboekModalForm');
	let actief_row = document.querySelector('tr.active');
	if (actief_row === undefined || actief_row === null) {
		document.getElementById('modal-titel').innerHTML = 'Je hebt geen selectie gemaakt!';
		document.getElementById('modal-titel').classList.add('text-danger');
		document.getElementById('editKasboek_save').style.visibility = 'hidden';
		document.getElementById('form_body').style.visibility = 'hidden';
	} else {
		change_jaar = false;
		change_rubriek = false;
		document.getElementById('modal-titel').innerHTML = 'Update kasboek';
		document.getElementById('modal-titel').classList.remove('text-danger');
		document.getElementById('form_body').style.visibility = 'visible';
		document.getElementById('editKasboek_save').style.visibility = 'visible';
		document.getElementById('editKasboek_save').innerHTML = 'Update kasboek';
		document.getElementById('jaartal').addEventListener('input', listener_change_jaar);
		document.getElementById('rubriek').addEventListener('input', listener_change_rubriek);
		
		document.getElementById('id').value = Kasboek_gegevens.id;
		document.getElementById('jaartal').value = Kasboek_gegevens.jaartal;
		document.getElementById('rubriek').value = Kasboek_gegevens.rubriekId;
		document.getElementById('omschrijving').value = Kasboek_gegevens.omschrijving;
		document.getElementById('datum').value = Kasboek_gegevens.datum;
		document.getElementById('inkomsten').value = Kasboek_gegevens.inkomsten;
		document.getElementById('uitgaven').value = Kasboek_gegevens.uitgaven;
	}
	document.getElementById('jaartal').focus();
	if (selectedJaar != 0) {
		document.getElementById('jaartal').value = selectedJaar;
		document.getElementById('rubriek').focus();
	};
	if (selectedRubriek != 0) {
		document.getElementById('rubriek').value = selectedRubriek;
		document.getElementById('omschrijving').focus();
	};
	window.onkeyup = function (event) {
		if(event.keyCode == 27) {
			listener_newKasboek_close(event);
		}
	};
	showUpdateKasboekModal(true);
};

function listener_updateKasboek_close(event) {
	event.preventDefault;
	showUpdateKasboekModal(false);
};

function listener_updateKasboek_submit() {
	event.preventDefault();
	const formData = new FormData(document.getElementById('editKasboekModalForm'));
	const data = put_Form('/kasboek/save_updateKasboek/' + selectedJaar + '/' + selectedRubriek + '/' + change_jaar + '/' + jaar, formData)
	.then((data) => {
		showUpdateKasboekModal(false);
		kasboek_tabel_refrech(data)
	})
	.catch((error) => {
		console.error('FOUT: ' + error);
	});
};

function showUpdateKasboekModal(show) {
	if(show) {
		document.getElementById('editKasboekModalForm').addEventListener('submit', listener_updateKasboek_submit, false);
		document.querySelector('.closeBtnX').addEventListener('click', listener_updateKasboek_close, false);
		document.getElementById('sluiten').addEventListener('click', listener_updateKasboek_close, false);
		kasboekModalAchtergrond.addEventListener('click', listener_updateKasboek_close, false);
		kasboekModalAchtergrond.classList.add('show');
		kasboekModal.classList.add('on');
		kasboekModalAchtergrond.classList.remove('hide');
		kasboekModal.classList.remove('off');
	} else {
		document.getElementById('editKasboekModalForm').reset();
		document.getElementById('jaartal').removeEventListener('input', listener_change_jaar);
		document.getElementById('rubriek').removeEventListener('input', listener_change_rubriek);
		document.getElementById('editKasboekModalForm').removeEventListener('submit', listener_updateKasboek_submit, false);
		document.querySelector('.closeBtnX').removeEventListener('click', listener_updateKasboek_close, false);
		document.getElementById('sluiten').removeEventListener('click', listener_updateKasboek_close, false);
		kasboekModalAchtergrond.removeEventListener('click', listener_updateKasboek_close, false);
		kasboekModalAchtergrond.classList.add('hide');
		kasboekModal.classList.add('off');
		kasboekModalAchtergrond.classList.remove('show');
		kasboekModal.classList.remove('on');
	}
};

/**
 * 
 * 
 * Delete kasboek
 * 
 * 
 */

function deleteKasboek() {
	console.log("Delete kasboek");
	const modal = document.querySelector('#kasboekModal');
	if(modal == null){
		let data = load_HTML('/kasboek/editKasboek')
		.then((data) => {
			document.getElementById("editKasboekModalHolder").innerHTML = data;
			setup_deleteKasboekModal();
		})
		.catch((error) => {
			console.error('FOUT: ' + error);
		});
	} else {
		setup_deleteKasboekModal();
	};
};

function setup_deleteKasboekModal() {
	kasboekModalAchtergrond = document.querySelector('#kasboekModalAchtergrond');
	kasboekModal = document.querySelector('#kasboekModal');
	kasboekModalForm = document.getElementById('editKasboekModalForm');
	const actief_row = document.querySelector('tr.active');
	if (actief_row === undefined || actief_row === null) {
		document.getElementById('modal-titel').innerHTML = 'Je hebt geen selectie gemaakt!';
		document.getElementById('editKasboek_save').style.visibility = 'hidden';
		document.getElementById('form_body').style.visibility = 'hidden';
	} else {
		document.getElementById('modal-titel').innerHTML = 'Delete kasboek!';
		document.getElementById('form_body').style.visibility = 'visible';
		document.getElementById('editKasboek_save').style.visibility = 'visible';
		document.getElementById('editKasboek_save').innerHTML = 'Delete kasboek';
		document.getElementById('id').value = Kasboek_gegevens.id;
		document.getElementById('jaartal').value = Kasboek_gegevens.jaartal;
		document.getElementById('rubriek').value = Kasboek_gegevens.rubriekId;
		document.getElementById('omschrijving').value = Kasboek_gegevens.omschrijving;
		document.getElementById('datum').value = Kasboek_gegevens.datum;
		document.getElementById('inkomsten').value = Kasboek_gegevens.inkomsten;
		document.getElementById('uitgaven').value = Kasboek_gegevens.uitgaven;
	}
	document.getElementById('modal-titel').classList.add('text-danger');
	document.getElementById('jaartal').focus();
	window.onkeyup = function (event) {
		if(event.keyCode == 27) {
			listener_newKasboek_close(event);
		}
	};
	showDeleteKasboekModal(true);
	
};

function listener_deleteKasboek_close(event) {
	event.preventDefault;
	showDeleteKasboekModal(false);
};

function listener_deleteKasboek_submit() {
	event.preventDefault();
	const formData = new FormData(document.getElementById('editKasboekModalForm'));
	const data = delete_Form('/kasboek/save_deleteKasboek/' + selectedJaar + '/' + selectedRubriek, formData)
	.then((data) => {
		showDeleteKasboekModal(false);
		kasboek_tabel_refrech(data)
	})
	.catch((error) => {
		console.error('FOUT: ' + error);
	});
};

function showDeleteKasboekModal(show) {
	if(show) {
		document.getElementById('editKasboekModalForm').addEventListener('submit', listener_deleteKasboek_submit, false);
		document.querySelector('.closeBtnX').addEventListener('click', listener_deleteKasboek_close, false);
		document.getElementById('sluiten').addEventListener('click', listener_deleteKasboek_close, false);
		kasboekModalAchtergrond.addEventListener('click', listener_deleteKasboek_close, false);
		kasboekModalAchtergrond.classList.add('show');
		kasboekModal.classList.add('on');
		kasboekModalAchtergrond.classList.remove('hide');
		kasboekModal.classList.remove('off');
	} else {
		document.getElementById('editKasboekModalForm').reset();
		document.getElementById('jaartal').removeEventListener('input', listener_change_jaar);
		document.getElementById('rubriek').removeEventListener('input', listener_change_rubriek);
		document.getElementById('editKasboekModalForm').removeEventListener('submit', listener_deleteKasboek_submit, false);
		document.querySelector('.closeBtnX').removeEventListener('click', listener_deleteKasboek_close, false);
		document.getElementById('sluiten').removeEventListener('click', listener_deleteKasboek_close, false);
		kasboekModalAchtergrond.removeEventListener('click', listener_deleteKasboek_close, false);
		kasboekModalAchtergrond.classList.add('hide');
		kasboekModal.classList.add('off');
		kasboekModalAchtergrond.classList.remove('show');
		kasboekModal.classList.remove('on');
	}
};
