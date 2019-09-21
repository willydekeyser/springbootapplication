/**
 * Leden aanpassen.
 */

/**
 * 
 * 
 * Nieuwe leden
 * 
 * 
 */

let lidModal;
let lidModalAchtergrond;
let lidModalForm;


function newLid() {
	console.log("New lid");
	let editModal = document.getElementById("editLidModal");
	if(typeof(editModal) == 'undefined' || editModal == null){
		let data = load_HTML('/leden/editLid')
		.then((data) => {
			document.getElementById("editLidModalHolder").innerHTML = data;
			setup_newLidModal();
		})
		.catch((error) => {
			console.error('FOUT: ' + error);
		});
	} else {
		setup_newLidModal();
	};
};

function setup_newLidModal() {
	lidModalAchtergrond = document.querySelector('#lidModalAchtergrond');
	lidModal = document.querySelector('#editLidModal');
	lidModalForm = document.getElementById('editLidModalForm');
	document.getElementById('lidmodaltitel').innerHTML = 'New lid!';
		
	showNewLidModal(true);
};

function listener_newLid_close(event) {
	event.preventDefault();
	showNewLidModal(false);
};

function listener_newLid_submit() {
	event.preventDefault();
	let formData = new FormData(document.getElementById('editLidModalForm'));
	let data = post_Form('/leden/save_newLid', formData)
	.then((data) => {
		selectedLidId = data.id;
		selectedSoortId = data.soortenleden.id;
		showNewLidModal(false);
		document.getElementById('select_leden').value = selectedSoortId;
		document.getElementById('select_leden').dispatchEvent(new Event('change'));
	})
	.catch((error) => {
		console.error('FOUT: ' + error);
	});
};

function showNewLidModal(show) {
	if(show) {
		document.getElementById('editLidModalForm').addEventListener('submit', listener_newLid_submit, false);
		document.getElementById('lidcloseBtnX').addEventListener('click', listener_newLid_close, false);
		document.getElementById('lidsluiten').addEventListener('click', listener_newLid_close, false);
		lidModalAchtergrond.addEventListener('click', listener_newLid_close, false);
		lidModalAchtergrond.classList.add('show');
		lidModal.classList.add('on');
		lidModalAchtergrond.classList.remove('hide');
		lidModal.classList.remove('off');
	} else {
		document.getElementById('editLidModalForm').reset();
		document.getElementById('editLidModalForm').removeEventListener('submit', listener_newLid_submit, false);
		document.getElementById('lidcloseBtnX').removeEventListener('click', listener_newLid_close, false);
		document.getElementById('lidsluiten').removeEventListener('click', listener_newLid_close, false);
		lidModalAchtergrond.removeEventListener('click', listener_newLid_close, false);
		lidModalAchtergrond.classList.add('hide');
		lidModal.classList.add('off');
		lidModalAchtergrond.classList.remove('show');
		lidModal.classList.remove('on');
	}
};

/**
 * 
 * 
 * Update leden
 * 
 * 
 */

function updateLid(id) {
	console.log("Update lid: " + id);	
	var editModal = document.getElementById("editLidModal");
	if(typeof(editModal) == 'undefined' || editModal == null){
		let data = load_HTML('/leden/editLid')
		.then((data) => {
			document.getElementById("editLidModalHolder").innerHTML = data;
			setup_updateLidModal();
			updateLid_Formvullen();
		})
		.catch((error) => {
			console.error('FOUT: ' + error);
		});
	} else {
		setup_updateLidModal();
		updateLid_Formvullen();
	};
};

function listener_updateLid_close(event) {
	event.preventDefault();
	showUpdateLidModal(false);
};

function setup_updateLidModal() {
	change_naam = false;
	change_soort = false;
	lidModalAchtergrond = document.querySelector('#lidModalAchtergrond');
	lidModal = document.querySelector('#editLidModal');
	lidModalForm = document.getElementById('editLidModalForm');
	document.getElementById('lidmodaltitel').innerHTML = 'Update lid!';
	document.getElementById('lidvoornaam').addEventListener('input', listener_change_naam);
	document.getElementById('lidfamilienaam').addEventListener('input', listener_change_naam);
	document.getElementById('lidsoortlid').addEventListener('change', listener_change_soortlid);
	showUpdateLidModal(true);
};

function updateLid_Formvullen(){
	console.log('ID: ' + leden_gegevens.voornaam + ' ' + leden_gegevens.familienaam);
	document.getElementById('lidid').value = leden_gegevens.id;
	document.getElementById('lidvoornaam').value = leden_gegevens.voornaam;
	document.getElementById('lidfamilienaam').value = leden_gegevens.familienaam;
	document.getElementById('lidstraat').value = leden_gegevens.straat;
	document.getElementById('lidnr').value = leden_gegevens.nr;
	document.getElementById('lidpostnr').value = leden_gegevens.postnr;
	document.getElementById('lidgemeente').value = leden_gegevens.gemeente;
	document.getElementById('lidemailadres').value = leden_gegevens.emailadres;
	document.getElementById('lidwebadres').value = leden_gegevens.webadres;
	document.getElementById('lidtelefoonnummer').value = leden_gegevens.telefoonnummer;
	document.getElementById('lidgsmnummer').value = leden_gegevens.gsmnummer;
	document.getElementById('liddatumlidgeld').value = leden_gegevens.datumlidgeld;
	document.getElementById('lidsoortlid').value = leden_gegevens.soortenleden.id;
	document.getElementById('lidontvangmail').checked = leden_gegevens.ontvangMail;
	document.getElementById('lidmailvlag').checked = leden_gegevens.mailVlag;
};

function listener_change_naam() {
	change_naam = true;
};

function listener_change_soortlid() {
	change_soort = true;
};

function listener_updateLid_submit() {
	event.preventDefault();
	let formData = new FormData(document.getElementById('editLidModalForm'));
	let data = put_Form('/leden/save_updateLid', formData)
	.then((data) => {
		selectedLidId = data.id;
		selectedSoortId = data.soortenleden.id;
		showNewLidModal(false);
		if(change_soort) {
			document.getElementById('select_leden').value = selectedSoortId;
			document.getElementById('select_leden').dispatchEvent(new Event('change'));
		} else if(change_naam) {
			leden_namenlijst_refrech();
			leden_gegevens_laden(data);
			leden_gegevens = data;
		} else {
			leden_gegevens_laden(data);
			leden_gegevens = data;
		}
	})
	.catch((error) => {
		console.error('FOUT: ' + error);
	});
};

function showUpdateLidModal(show) {
	if(show) {
		document.getElementById('editLidModalForm').addEventListener('submit', listener_updateLid_submit, false);
		document.getElementById('lidcloseBtnX').addEventListener('click', listener_updateLid_close, false);
		document.getElementById('lidsluiten').addEventListener('click', listener_updateLid_close, false);
		lidModalAchtergrond.addEventListener('click', listener_updateLid_close, false);
		lidModalAchtergrond.classList.add('show');
		lidModal.classList.add('on');
		lidModalAchtergrond.classList.remove('hide');
		lidModal.classList.remove('off');
	} else {
		document.getElementById('editLidModalForm').reset();
		document.getElementById('editLidModalForm').removeEventListener('submit', listener_updateLid_submit, false);
		document.getElementById('lidcloseBtnX').removeEventListener('click', listener_updateLid_close, false);
		document.getElementById('lidsluiten').removeEventListener('click', listener_updateLid_close, false);
		lidModalAchtergrond.removeEventListener('click', listener_updateLid_close, false);
		lidModalAchtergrond.classList.add('hide');
		lidModal.classList.add('off');
		lidModalAchtergrond.classList.remove('show');
		lidModal.classList.remove('on');
	}
};

/**
 * 
 * 
 * Delete leden
 * 
 * 
 */

function deleteLid(id) {
	console.log("Delete lid: " + id);
	var deleteModal = document.getElementById("deleteLidModal");
	if(typeof(deleteModal) == 'undefined' || deleteModal == null){
		let data = load_HTML('/leden/deleteLid')
		.then((data) => {
			document.getElementById("deleteLidModalHolder").innerHTML = data;
			setup_deleteLidModal();
		})
		.catch((error) => {
			console.error('FOUT: ' + error);
		});
	} else {
		setup_deleteLidModal();
	};
};

function listener_deleteLid_close(event) {
	event.preventDefault();
	showDeleteLidModal(false);
};

function setup_deleteLidModal() {
	lidModalAchtergrond = document.querySelector('#lidModalAchtergrond');
	lidModal = document.querySelector('#deleteLidModal');
	lidModalForm = document.getElementById('deleteLidModalForm');
	if(leden_gegevens.lidgelden.length == 0){
		document.getElementById('deleteSubmit').style.visibility = 'visible';
		document.getElementById('delete_form_body').style.visibility = 'visible';
		document.getElementById('delete_form_body_error').style.visibility = 'hidden';
		document.getElementById('delete_id').value = leden_gegevens.id;
		document.getElementById('delete_naam').value = leden_gegevens.voornaam + ' ' + leden_gegevens.familienaam;
	} else {
		document.getElementById('deleteSubmit').style.visibility = 'hidden';
		document.getElementById('delete_form_body').style.visibility = 'hidden';
		document.getElementById('delete_form_body_error').style.visibility = 'visible';
		document.getElementById('delete_form_body_error_naam').innerHTML = leden_gegevens.voornaam + ' ' + leden_gegevens.familienaam;
	}
	document.getElementById('delete-modal-titel').innerHTML = 'Verwijder lid!';
	showDeleteLidModal(true);
};

function showDeleteLidModal(show) {
	if(show) {
		document.getElementById('deleteLidModalForm').addEventListener('submit', listener_deleteLid_submit, false);
		document.getElementById('deletecloseBtnX').addEventListener('click', listener_deleteLid_close, false);
		document.getElementById('deletesluiten').addEventListener('click', listener_deleteLid_close, false);
		lidModalAchtergrond.addEventListener('click', listener_deleteLid_close, false);
		lidModalAchtergrond.classList.add('show');
		lidModal.classList.add('on');
		lidModalAchtergrond.classList.remove('hide');
		lidModal.classList.remove('off');
	} else {
		document.getElementById('deleteLidModalForm').reset();
		document.getElementById('deleteLidModalForm').removeEventListener('submit', listener_deleteLid_submit, false);
		document.getElementById('deletecloseBtnX').removeEventListener('click', listener_deleteLid_close, false);
		document.getElementById('deletesluiten').removeEventListener('click', listener_deleteLid_close, false);
		lidModalAchtergrond.removeEventListener('click', listener_deleteLid_close, false);
		lidModalAchtergrond.classList.add('hide');
		lidModal.classList.add('off');
		lidModalAchtergrond.classList.remove('show');
		lidModal.classList.remove('on');
	}
};

function listener_deleteLid_submit() {
	event.preventDefault();
	let formData = new FormData(document.getElementById('deleteLidModalForm'));
	let data = delete_Form('/leden/save_deleteLid', formData)
	.then((data) => {
		showDeleteLidModal(false);
		selectedLidId = data;
		document.getElementById('select_leden').value = selectedSoortId;
		document.getElementById('select_leden').dispatchEvent(new Event('change'));
	})
	.catch((error) => {
	console.error('FOUT: ' + error);
	});
};
