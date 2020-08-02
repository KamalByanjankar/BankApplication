var open = 'fa-eye';
var close = 'fa-eye-slash';
var password = document.getElementById('password');
var confirmPassword = document.getElementById('confirmPassword');

document.getElementById('toggleShowPassword').onclick = function() {
	if( this.classList.contains(open)) {
		password.type="text";
	    this.classList.remove(open);
	    this.className += ' '+close;
	}
	else {
		password.type="password";
	    this.classList.remove(close);
	    this.className += ' '+open;
  }
}

document.getElementById('toggleShowConfirmPassword').onclick = function() {
	if( this.classList.contains(open)) {
		confirmPassword.type="text";
	    this.classList.remove(open);
	    this.className += ' '+close;
	}
	else {
		confirmPassword.type="password";
	    this.classList.remove(close);
	    this.className += ' '+open;
  }
}