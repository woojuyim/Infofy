function toggleSection() {
	var section = document.getElementById("show");
	if (section.offsetWidth > 0 || section.offsetHeight > 0) {
		section.style.display = 'none';
	} else {
		section.style.display = 'block';
	}
}