$('document').ready(function(){
	
	var password = document.getElementById("newPassword");
	var confirmPassword = document.getElementById("confirmPassword");
	
	function validatePassword() {
		if(password.value != confirmPassword.value) {
			confirmPassword.setCustomValidity("Password Don't Match");
		}
		
		else {
			confirmPassword.setCustomValidity("");
		}
	}
	
	password.onchange = validatePassword;
	confirmPassword.onkeyup = validatePassword;
})