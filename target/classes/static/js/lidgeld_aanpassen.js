/**
 *  Lidgeld aanpassen.
 */
/**
 * 
 * 
 * Nieuw lidgeld
 * 
 * 
 */

"use strict";

let lidgeldModal;
let lidgeldledenModalAchtergrond;
let lidgeldledenModalForm;
let lidgeldModalForm;

function newLidgeld(id){
	console.log("New lidgeld: " + id);
	const modal = document.querySelector('#editLidgeldModal');
	if(modal == null){
		let data = load_HTML('/leden/editLidgeld')
		.then((data) => {
			document.getElementById("editLidgeldModalHolder").innerHTML = data;
			setup_newLidgeldModal();
		})
		.catch((error) => {
			console.error('FOUT: ' + error);
		});
	} else {
		setup_newLidgeldModal();
	};
};

function setup_newLidgeldModal() {
	lidgeldModalAchtergrond = document.querySelector('#lidgeldModalAchtergrond');
	lidgeldModal = document.querySelector('#editLidgeldModal');
	lidgeldModalForm = document.getElementById('editLidgeldModalForm');
	document.getElementById('modal-titel').innerHTML = 'New lidgeld!';
	document.getElementById('modal-titel').classList.remove('text-danger');
	document.getElementById('editLidgeld_save').style.visibility = 'visible';
	document.getElementById('editLidgeld_save').innerHTML = 'Save lidgeld';
	document.getElementById('editLidgeld_id').value = '';
	document.getElementById('editLidgeld_naam').value = leden_gegevens.voornaam + ' ' + leden_gegevens.familienaam;
	document.getElementById('editLidgeld_naam_id').value = leden_gegevens.leden_id;
	document.getElementById('editLidgeld_datum').readOnly = false;
	document.getElementById('editLidgeld_bedrag').readOnly = false;
	document.getElementById('editLidgeld_datum').focus();
	window.onkeyup = function (event) {
		if(event.keyCode == 27) {
			listener_newLidgeld_close(event);
		}
	};
	showNewLidgeldModal(true);
};

function listener_newLidgeld_close(event) {
	event.preventDefault;
	showNewLidgeldModal(false);
};

function listener_newLidgeld_submit() {
	event.preventDefault();
	const formData = new FormData(document.getElementById('editLidgeldModalForm'));
	const data = post_Form('/lidgeld/save_newLidgeld', formData)
	.then((data) => {
		showNewLidgeldModal(false);
		leden_lidgeld_laden(data)
		leden_gegevens.lidgelden = data;
	})
	.catch((error) => {
		console.error('FOUT: ' + error);
	});
};

function showNewLidgeldModal(show) {
	if(show) {
		document.getElementById('editLidgeldModalForm').addEventListener('submit', listener_newLidgeld_submit, false);
		document.querySelector('.closeBtnX').addEventListener('click', listener_newLidgeld_close, false);
		document.getElementById('sluiten').addEventListener('click', listener_newLidgeld_close, false);
		lidgeldModalAchtergrond.addEventListener('click', listener_newLidgeld_close, false);
		lidgeldModalAchtergrond.classList.add('show');
		lidgeldModal.classList.add('on');
		lidgeldModalAchtergrond.classList.remove('hide');
		lidgeldModal.classList.remove('off');
	} else {
		document.getElementById('editLidgeldModalForm').reset();
		document.getElementById('editLidgeldModalForm').removeEventListener('submit', listener_newLidgeld_submit, false);
		document.querySelector('.closeBtnX').removeEventListener('click', listener_newLidgeld_close, false);
		document.getElementById('sluiten').removeEventListener('click', listener_newLidgeld_close, false);
		lidgeldModalAchtergrond.removeEventListener('click', listener_newLidgeld_close, false);
		lidgeldModalAchtergrond.classList.add('hide');
		lidgeldModal.classList.add('off');
		lidgeldModalAchtergrond.classList.remove('show');
		lidgeldModal.classList.remove('on');
	}
};

/**
 * 
 * 
 * Update lidgeld
 * 
 * 
 */

function updateLidgeld(id){
	console.log("Update lidgeld: " + id + " - " + lidgeldId);
	const modal = document.querySelector('#editLidgeldModal');
	if(modal == null){
		let data = load_HTML('/leden/editLidgeld')
		.then((data) => {
			document.getElementById("editLidgeldModalHolder").innerHTML = data;
			setup_updateLidgeldModal();
		})
		.catch((error) => {
			console.error('FOUT: ' + error);
		});
	} else {
		setup_updateLidgeldModal();
	};
};

function setup_updateLidgeldModal() {
	lidgeldModalAchtergrond = document.querySelector('#lidgeldModalAchtergrond');
	lidgeldModal = document.querySelector('#editLidgeldModal');
	lidgeldModalForm = document.getElementById('editLidgeldModalForm');
	const actief_row = document.querySelector('tr.active');
	if (actief_row === undefined || actief_row === null) {
		document.getElementById('modal-titel').innerHTML = 'Je hebt geen selectie gemaakt!';
		document.getElementById('modal-titel').classList.add('text-danger');
		document.getElementById('editLidgeld_save').disabled = true;
		document.getElementById('editLidgeld_save').style.visibility = 'hidden';
	} else {
		document.getElementById('modal-titel').innerHTML = 'Update lidgeld!';
		document.getElementById('modal-titel').classList.remove('text-danger');
		document.getElementById('editLidgeld_save').disabled = false;
		document.getElementById('editLidgeld_save').style.visibility = 'visible';
		document.getElementById('editLidgeld_save').innerHTML = 'Update lidgeld';
		const index = actief_row.rowIndex - 1;
		const id = leden_gegevens.lidgelden[index].id;
		const datum = leden_gegevens.lidgelden[index].datum;
		const bedrag = leden_gegevens.lidgelden[index].bedrag;
		document.getElementById('editLidgeld_id').value = id;
		document.getElementById('editLidgeld_datum').value = datum;
		document.getElementById('editLidgeld_bedrag').value = bedrag;
		document.getElementById('editLidgeld_datum').readOnly = false;
		document.getElementById('editLidgeld_bedrag').readOnly = false
	};
	if(leden_gegevens.voornaam === undefined || leden_gegevens.familienaam === undefined || leden_gegevens.leden_id === undefined) {
		document.getElementById('editLidgeld_naam').value = '';
		document.getElementById('editLidgeld_naam_id').value = '';
	} else {
		document.getElementById('editLidgeld_naam').value = leden_gegevens.voornaam + ' ' + leden_gegevens.familienaam;
		document.getElementById('editLidgeld_naam_id').value = leden_gegevens.leden_id;
	}

	
	document.getElementById('editLidgeld_datum').focus();
	window.onkeyup = function (event) {
		if(event.keyCode == 27) {
			listener_updateLidgeld_close(event);
		}
	};
	showUpdateLidgeldModal(true);
};

function listener_updateLidgeld_close(event) {
	event.preventDefault;
	showUpdateLidgeldModal(false);
};

function listener_updateLidgeld_submit() {
	event.preventDefault();
	const formData = new FormData(document.getElementById('editLidgeldModalForm'));
	const data = put_Form('/lidgeld/save_updateLidgeld', formData)
	.then((data) => {
		showUpdateLidgeldModal(false);
		leden_lidgeld_laden(data);
		leden_gegevens.lidgelden = data;
	})
	.catch((error) => {
		console.error('FOUT: ' + error);
	});
};

function showUpdateLidgeldModal(show) {
	if(show) {
		document.getElementById('editLidgeldModalForm').addEventListener('submit', listener_updateLidgeld_submit, false);
		document.querySelector('.closeBtnX').addEventListener('click', listener_updateLidgeld_close, false);
		document.getElementById('sluiten').addEventListener('click', listener_updateLidgeld_close, false);
		lidgeldModalAchtergrond.addEventListener('click', listener_updateLidgeld_close, false);
		lidgeldModalAchtergrond.classList.add('show');
		lidgeldModal.classList.add('on');
		lidgeldModalAchtergrond.classList.remove('hide');
		lidgeldModal.classList.remove('off');
	} else {
		document.getElementById('editLidgeldModalForm').reset();
		document.getElementById('editLidgeldModalForm').removeEventListener('submit', listener_updateLidgeld_submit, false);
		document.querySelector('.closeBtnX').removeEventListener('click', listener_updateLidgeld_close, false);
		document.getElementById('sluiten').removeEventListener('click', listener_updateLidgeld_close, false);
		lidgeldModalAchtergrond.removeEventListener('click', listener_updateLidgeld_close, false);
		lidgeldModalAchtergrond.classList.add('hide');
		lidgeldModal.classList.add('off');
		lidgeldModalAchtergrond.classList.remove('show');
		lidgeldModal.classList.remove('on');
	}
};

/**
 * 
 * 
 * Delete lidgeld
 * 
 * 
 */

function deleteLidgeld(id){
	console.log("Delete lidgeld: " + id + " - " + lidgeldId);
	const modal = document.querySelector('#editLidgeldModal');
	if(modal == null){
		const data = load_HTML('/leden/editLidgeld')
		.then((data) => {
			document.getElementById("editLidgeldModalHolder").innerHTML = data;
			setup_deleteLidgeldModal();
		})
		.catch((error) => {
			console.error('FOUT: ' + error);
		});
	} else {
		setup_deleteLidgeldModal();
	};
};

function setup_deleteLidgeldModal() {
	lidgeldModalAchtergrond = document.querySelector('#lidgeldModalAchtergrond');
	lidgeldModal = document.querySelector('#editLidgeldModal');
	lidgeldModalForm = document.getElementById('editLidgeldModalForm');
	const actief_row = document.querySelector('tr.active');
	if (actief_row === undefined || actief_row === null) {
		document.getElementById('modal-titel').innerHTML = 'Je hebt geen selectie gemaakt!';
		document.getElementById('editLidgeld_save').disabled = true;
		document.getElementById('editLidgeld_save').style.visibility = 'hidden';
	} else {
		document.getElementById('modal-titel').innerHTML = 'Delete lidgeld!';
		document.getElementById('editLidgeld_save').disabled = false;
		document.getElementById('editLidgeld_save').style.visibility = 'visible';
		document.getElementById('editLidgeld_save').innerHTML = 'Delete lidgeld';
		const index = actief_row.rowIndex - 1;
		const id = leden_gegevens.lidgelden[index].id;
		const datum = leden_gegevens.lidgelden[index].datum;
		const bedrag = leden_gegevens.lidgelden[index].bedrag;
		document.getElementById('editLidgeld_id').value = id;
		document.getElementById('editLidgeld_datum').value = datum;
		document.getElementById('editLidgeld_bedrag').value = bedrag;
	};
	document.getElementById('modal-titel').classList.add('text-danger');
	document.getElementById('editLidgeld_naam').value = leden_gegevens.voornaam + ' ' + leden_gegevens.familienaam;
	document.getElementById('editLidgeld_naam_id').value = leden_gegevens.leden_id;
	document.getElementById('editLidgeld_datum').readOnly = false;
	document.getElementById('editLidgeld_bedrag').readOnly = true;
	document.getElementById('editLidgeld_id').focus();
	window.onkeyup = function (event) {
		if(event.keyCode == 27) {
			listener_deleteLidgeld_close(event);
		}
	};
	showDeleteLidgeldModal(true);
};

function listener_deleteLidgeld_close(event) {
	event.preventDefault;
	showDeleteLidgeldModal(false);
};

function listener_deleteLidgeld_submit() {
	event.preventDefault();
	const formData = new FormData(document.getElementById('editLidgeldModalForm'));
	const data = delete_Form('/lidgeld/save_deleteLidgeld', formData)
	.then((data) => {
		showDeleteLidgeldModal(false);
		leden_lidgeld_laden(data);
		leden_gegevens.lidgelden = data;
	})
	.catch((error) => {
		console.error('FOUT: ' + error);
	});
};

function showDeleteLidgeldModal(show) {
	if(show) {
		document.getElementById('editLidgeldModalForm').addEventListener('submit', listener_deleteLidgeld_submit, false);
		document.querySelector('.closeBtnX').addEventListener('click', listener_deleteLidgeld_close, false);
		document.getElementById('sluiten').addEventListener('click', listener_deleteLidgeld_close, false);
		lidgeldModalAchtergrond.addEventListener('click', listener_deleteLidgeld_close, false);
		lidgeldModalAchtergrond.classList.add('show');
		lidgeldModal.classList.add('on');
		lidgeldModalAchtergrond.classList.remove('hide');
		lidgeldModal.classList.remove('off');
	} else {
		document.getElementById('editLidgeldModalForm').reset();
		document.getElementById('editLidgeldModalForm').removeEventListener('submit', listener_deleteLidgeld_submit, false);
		document.querySelector('.closeBtnX').removeEventListener('click', listener_deleteLidgeld_close, false);
		document.getElementById('sluiten').removeEventListener('click', listener_deleteLidgeld_close, false);
		lidgeldModalAchtergrond.removeEventListener('click', listener_deleteLidgeld_close, false);
		lidgeldModalAchtergrond.classList.add('hide');
		lidgeldModal.classList.add('off');
		lidgeldModalAchtergrond.classList.remove('show');
		lidgeldModal.classList.remove('on');
	}
};

/**
 * 
 * 
 * Max lidgeld lijst
 * 
 * 
 */

function maxLidgeld(id){
	console.log("New MAX lidgeld: " + id);
	const modal = document.querySelector('#editLidgeldModal');
	if(modal == null){
		let data = load_HTML('/leden/editLidgeld')
		.then((data) => {
			document.getElementById("editLidgeldModalHolder").innerHTML = data;
			setup_maxLidgeldModal();
		})
		.catch((error) => {
			console.error('FOUT: ' + error);
		});
	} else {
		setup_maxLidgeldModal();
	};
};

function setup_maxLidgeldModal() {
	let naam;
	let ledenid;
	lidgeldModalAchtergrond = document.querySelector('#lidgeldModalAchtergrond');
	lidgeldModal = document.querySelector('#editLidgeldModal');
	lidgeldModalForm = document.getElementById('editLidgeldModalForm');
	const actief_row = document.querySelector('tr.active');
	if (actief_row === undefined || actief_row === null) {
		document.getElementById('modal-titel').innerHTML = 'Je hebt geen selectie gemaakt!';
		document.getElementById('modal-titel').classList.add('text-danger');
		document.getElementById('editLidgeld_save').disabled = true;
		document.getElementById('editLidgeld_save').style.visibility = 'hidden';
		document.getElementById('editLidgeld_naam').value = '';
		document.getElementById('editLidgeld_naam_id').value = '';
	} else {
		document.getElementById('modal-titel').innerHTML = 'Nieuw lidgeld!';
		document.getElementById('modal-titel').classList.remove('text-danger');
		document.getElementById('editLidgeld_save').disabled = false;
		document.getElementById('editLidgeld_save').style.visibility = 'visible';
		document.getElementById('editLidgeld_save').innerHTML = 'Nieuw lidgeld';
		const index = actief_row.rowIndex - 1;
		const id = lidgeldData[index].id;
		naam = lidgeldData[index].leden.voornaam + ' ' + lidgeldData[index].leden.familienaam;
		ledenid = lidgeldData[index].leden.leden_id;
		document.getElementById('editLidgeld_id').value = ledenid;
		document.getElementById('editLidgeld_naam').value = naam;
		document.getElementById('editLidgeld_naam_id').value = ledenid;
		document.getElementById('editLidgeld_datum').readOnly = false;
		document.getElementById('editLidgeld_bedrag').readOnly = false
	};
	document.getElementById('editLidgeld_datum').focus();
	window.onkeyup = function (event) {
		if(event.keyCode == 27) {
			listener_maxLidgeld_close(event);
		}
	};
	showmaxLidgeldModal(true);
};

function listener_maxLidgeld_close(event) {
	event.preventDefault;
	showmaxLidgeldModal(false);
};

function listener_maxLidgeld_submit() {
	event.preventDefault();
	const formData = new FormData(document.getElementById('editLidgeldModalForm'));
	const data = post_Form('/lidgeld/save_newLidgeld', formData)
	.then((data) => {
		showmaxLidgeldModal(false);
		max_lidgeld_Tabel_laden();
		//max_lidgeld_start();
	})
	.catch((error) => {
		console.error('FOUT: ' + error);
	});
};

function showmaxLidgeldModal(show) {
	if(show) {
		document.getElementById('editLidgeldModalForm').addEventListener('submit', listener_maxLidgeld_submit, false);
		document.querySelector('.closeBtnX').addEventListener('click', listener_maxLidgeld_close, false);
		document.getElementById('sluiten').addEventListener('click', listener_maxLidgeld_close, false);
		lidgeldModalAchtergrond.addEventListener('click', listener_maxLidgeld_close, false);
		lidgeldModalAchtergrond.classList.add('show');
		lidgeldModal.classList.add('on');
		lidgeldModalAchtergrond.classList.remove('hide');
		lidgeldModal.classList.remove('off');
	} else {
		document.getElementById('editLidgeldModalForm').reset();
		document.getElementById('editLidgeldModalForm').removeEventListener('submit', listener_maxLidgeld_submit, false);
		document.querySelector('.closeBtnX').removeEventListener('click', listener_maxLidgeld_close, false);
		document.getElementById('sluiten').removeEventListener('click', listener_maxLidgeld_close, false);
		lidgeldModalAchtergrond.removeEventListener('click', listener_maxLidgeld_close, false);
		lidgeldModalAchtergrond.classList.add('hide');
		lidgeldModal.classList.add('off');
		lidgeldModalAchtergrond.classList.remove('show');
		lidgeldModal.classList.remove('on');
	}
};

/**
 * 
 * 
 * Max lidgeld E-mail versturen
 * 
 * 
 */

function maxLidgeldEmail(id){
	console.log("New MAX lidgeld E-mail versturen: " + id);
	const modal = document.querySelector('#editLidgeldModal');
	if(modal == null){
		let data = load_HTML('/leden/editLidgeld')
		.then((data) => {
			document.getElementById("editLidgeldModalHolder").innerHTML = data;
			setup_maxLidgeldEmailModal();
		})
		.catch((error) => {
			console.error('FOUT: ' + error);
		});
	} else {
		setup_maxLidgeldEmailModal();
	};
};

function setup_maxLidgeldEmailModal() {
	let naam;
	let ledenid;
	let datum;
	let bedrag;
	let email;
	lidgeldModalAchtergrond = document.querySelector('#lidgeldModalAchtergrond');
	lidgeldModal = document.querySelector('#editLidgeldModal');
	lidgeldModalForm = document.getElementById('editLidgeldModalForm');
	document.getElementById('editLidgeld_datum').readOnly = true;
	document.getElementById('editLidgeld_bedrag').readOnly = true;
	const actief_row = document.querySelector('tr.active');
	if (actief_row === undefined || actief_row === null) {
		document.getElementById('modal-titel').innerHTML = 'Je hebt geen selectie gemaakt!';
		document.getElementById('modal-titel').classList.add('text-danger');
		document.getElementById('editLidgeld_save').disabled = true;
		document.getElementById('editLidgeld_save').style.visibility = 'hidden';
		document.getElementById('editLidgeld_naam').value = '';
		document.getElementById('editLidgeld_naam_id').value = '';
	} else {
		const index = actief_row.rowIndex - 1;
		const id = lidgeldData[index].id;
		naam = lidgeldData[index].leden.voornaam + ' ' + lidgeldData[index].leden.familienaam;
		ledenid = lidgeldData[index].leden.leden_id;
		datum = lidgeldData[index].datum;
		bedrag = lidgeldData[index].bedrag;
		email = lidgeldData[index].leden.emailadres;
		if(email === null || email === "") {
			document.getElementById('modal-titel').innerHTML = naam + '<br>heeft geen e-mail adres!';
			document.getElementById('modal-titel').classList.add('text-danger');
			document.getElementById('editLidgeld_save').disabled = true;
			document.getElementById('editLidgeld_save').style.visibility = 'hidden';
		} else {
			document.getElementById('modal-titel').innerHTML = 'E-mail versturen';
			document.getElementById('modal-titel').classList.remove('text-danger');
			document.getElementById('editLidgeld_save').disabled = false;
			document.getElementById('editLidgeld_save').style.visibility = 'visible';
			document.getElementById('editLidgeld_save').innerHTML = 'E-mail versturen';
		}
		document.getElementById('editLidgeld_id').value = ledenid;
		document.getElementById('editLidgeld_naam').value = naam;
		document.getElementById('editLidgeld_naam_id').value = ledenid;
		document.getElementById('editLidgeld_datum').value = datum;
		document.getElementById('editLidgeld_bedrag').value = bedrag;
	};
	document.getElementById('editLidgeld_datum').focus();
	window.onkeyup = function (event) {
		if(event.keyCode == 27) {
			listener_maxLidgeld_close(event);
		}
	};
	showmaxLidgeldEmailModal(true);
};

function listener_maxLidgeldEmail_close(event) {
	event.preventDefault;
	showmaxLidgeldEmailModal(false);
};

function listener_maxLidgeldEmail_submit() {
	event.preventDefault();
	const formData = new FormData(document.getElementById('editLidgeldModalForm'));
	const data = post_Form('/mail/lidgeldMail', formData)
	.then((data) => {
		showmaxLidgeldEmailModal(false);
	})
	.catch((error) => {
		console.error('FOUT: ' + error);
	});
};

function showmaxLidgeldEmailModal(show) {
	if(show) {
		document.getElementById('editLidgeldModalForm').addEventListener('submit', listener_maxLidgeldEmail_submit, false);
		document.querySelector('.closeBtnX').addEventListener('click', listener_maxLidgeldEmail_close, false);
		document.getElementById('sluiten').addEventListener('click', listener_maxLidgeldEmail_close, false);
		lidgeldModalAchtergrond.addEventListener('click', listener_maxLidgeldEmail_close, false);
		lidgeldModalAchtergrond.classList.add('show');
		lidgeldModal.classList.add('on');
		lidgeldModalAchtergrond.classList.remove('hide');
		lidgeldModal.classList.remove('off');
	} else {
		document.getElementById('editLidgeldModalForm').reset();
		document.getElementById('editLidgeldModalForm').removeEventListener('submit', listener_maxLidgeldEmail_submit, false);
		document.querySelector('.closeBtnX').removeEventListener('click', listener_maxLidgeldEmail_close, false);
		document.getElementById('sluiten').removeEventListener('click', listener_maxLidgeldEmail_close, false);
		lidgeldModalAchtergrond.removeEventListener('click', listener_maxLidgeldEmail_close, false);
		lidgeldModalAchtergrond.classList.add('hide');
		lidgeldModal.classList.add('off');
		lidgeldModalAchtergrond.classList.remove('show');
		lidgeldModal.classList.remove('on');
	}
};