/*
 *  functions.
 *
*/

/*
 * Setup GRID.
 *
*/

"use strict";

function reset_grid() {
	section_height(10, 100, 20);
	menu_main_width(300);
	document.getElementById('main_section_header').innerHTML = ""; 
	document.getElementById('main_section_main').innerHTML = "";
	document.getElementById('main_section_footer').innerHTML = "";
	document.getElementById('menu_header').innerHTML = ""; 
	document.getElementById('menu_main').innerHTML = "";
};

function section_height(height_header, height_main, height_footer) {
	document.getElementById('main_section_header').style.flexBasis = height_header + 'rem';
	document.getElementById('main_section_main').style.flexBasis = height_main + 'rem';
	document.getElementById('main_section_footer').style.flexBasis = height_footer + 'rem';
}

function menu_height(height_header, height_main) {
	document.getElementById('menu_header').style.flexBasis = height_header + 'rem';
	document.getElementById('menu_main').style.flexBasis = height_main + 'rem';
}

function menu_main_width(width) {
	document.getElementById('grid_container').style.gridTemplateColumns = width + 'px auto';
}

/*
 * Fetch functions.
 *
*/

async function Refrech_HTML(url, div) {
	console.log('Refrech HTML: ' + url);
	let response = await fetch(url, {
		headers: {
			"Header" : "Willy De Keyser"
		}
	});
	if (!response.ok || !response.status == 200 || response.redirected) {
		console.log('error: ' + response.status + ' - ' + 'Redirected: ' + response.redirected);
		window.open('/', '_self');
		throw Error(response.status);
	}
	let tekst = await response.text();
	document.getElementById(div).innerHTML = tekst;
	console.log('ok: ' + response.status + ' - ' + 'Redirected: ' + response.redirected);
	console.log(div + ' geladen - ' + url);
	return 'OK';
};

async function existRecord(url) {
	let response = await fetch(url, {
		headers: {
			"Header" : "Willy De Keyser"
		}
	});
	if (!response.ok || !response.status == 200 || response.redirected) {
		console.log('error: ' + response.status + ' - ' + 'Redirected: ' + response.redirected);
		window.open('/', '_self');
		throw Error(response.status);
	};
	return await response.text();
}

async function load_HTML(url) {
	console.log('Load HTML: ' + url);
	let response = await fetch(url, {
		headers: {
			"Header" : "Willy De Keyser"
		}
	});
	if (!response.ok || !response.status == 200 || response.redirected) {
		console.log('error: ' + response.status + ' - ' + 'Redirected: ' + response.redirected);
		window.open('/', '_self');
		throw Error(response.status);
	};
	console.log('ok: ' + response.status + ' - ' + 'Redirected: ' + response.redirected);
	return await response.text();
};

async function Load_JSON(url, div) {
	console.log('Load JSON: ' + url);
	let response = await fetch(url, {
		headers: {
			"Header" : "Willy De Keyser"
		}
	});
	if (!response.ok || !response.status == 200 || response.redirected) {
		console.log('error: ' + response.status + ' - ' + 'Redirected: ' + response.redirected);
		window.open('/', '_self');
		throw Error(response.status);
	};
	let data = await response.text();
	let myJson = await JSON.stringify(data);
	console.log(div + ' - ' + data);
	console.log(myJson);
	document.getElementById(div).innerHTML = data; 
	return 'OK';
};

async function fetch_JSON(url) {
	let response = await fetch(url, {
		headers: {
			"Header" : "Willy De Keyser"
		}
	});
	if (!response.ok || !response.status == 200 || response.redirected) {
		console.log('error: ' + response.status + ' - ' + 'Redirected: ' + response.redirected);
		window.open('/', '_self');
		throw Error(response.status);
	};
	let data = await response.json();
	console.log('Fetch JSON data');
	return data;
};

async function fetch_TEXT(url) {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	console.log("fetch: " + token + " - " + header);
	let response = await fetch(url, {
		method: "POST",
		credentials: 'same-origin',
		headers: {
			'Header' : 'Willy De Keyser',
			'X-Requested-With': 'XMLHttpRequest',
			'X-CSRF-Token' : token
		}
	});
	if (!response.ok || !response.status == 200 || response.redirected) {
		console.log('error: ' + response.status + ' - ' + 'Redirected: ' + response.redirected);
		window.open('/', '_self');
		throw Error(response.status);
	};
	let data = await response.text();
	console.log('Fetch TEXT data');
	return data;
}

async function post_Form(url, form) {
	console.log('Post Form: ' + url + ' - ' + form);
	let response = await fetch(url, {
		method: "POST",
		body: form.serialize(),
		headers: {
	        "Content-Type": "application/x-www-form-urlencoded",
	        "Header" : "Willy De Keyser"
	    }
	});
	if (!response.ok || response.error) {
		window.open('/', '_self');
		throw Error(response.status);
	}
	return await response.json();
};

async function put_Form(url, form) {
	console.log('Put Form: ' + url + ' - ' + form);
	let response = await fetch(url, {
		method: "PUT",
		body: form.serialize(),
		headers: {
	        "Content-Type": "application/x-www-form-urlencoded",
	        "Header" : "Willy De Keyser"
	    },
	});
	if (!response.ok || response.error) {
		window.open('/', '_self');
		throw Error(response.status);
	}
	return await response.json();
};

async function delete_Form(url, form) {
	console.log('Delete Form: ' + url + ' - ' + form);
	let response = await fetch(url, {
		method: "DELETE",
		body: form.serialize(),
		headers: {
	        "Content-Type": "application/x-www-form-urlencoded",
	        "Header" : "Willy De Keyser"
	    },
	});
	if (!response.ok || response.error) {
		window.open('/', '_self');
		throw Error(response.status);
	}
	return await response.json();
};

/*
 * Help functions.
 *
*/

function delay(time) {
	return new Promise(resolve => {
		setTimeout(() => {
			resolve('Delay: ' + time);
		}, time * 1000);
	});
};

function getFormattedDate(datum) {
	let date = new Date(datum);
	let year = date.getFullYear();
	let month = (1 + date.getMonth()).toString();
	month = month.length > 1 ? month : '0' + month;

	let day = date.getDate().toString();
	day = day.length > 1 ? day : '0' + day;
	  
	return day + '/' + month + '/' + year;
};

function getFormattedEuro(euro) {
	const formatter = new Intl.NumberFormat('nl-NL', {
		style: 'currency',
		currency: 'EUR',
		minimumFractionDigits: 2
	});
	return formatter.format(euro);
}

function FormDataToJSON(FormElement){    
    var formData = new FormData(FormElement);
    var ConvertedJSON= {};
    for (const [key, value]  of formData.entries())
    {
        ConvertedJSON[key] = value;
    }
    return ConvertedJSON
};