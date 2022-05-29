const manageForms = Vue.createApp({
	data() {
		return {
			descriptionInput: "",
			descriptionError: "",
			addressInput: "",
			addressError: "",
			cityInput: "",
			cityError: "",
			postalCodeInput: null,
			postalCodeError: "",
			countryInput: "",
			countryError: "",
			checkboxConstraintsError: "",
			checkboxServicesError: "",
			pn: null,
		}
	},
	methods: {

		addHouse: function(e) {

			console.log("formControl");

			///// House description/////
			if (!this.descriptionInput == "") {
				this.$refs.descriptionError.innerText = "";
			} else {
				e.preventDefault();
				this.$refs.descriptionError.innerText = "Description cannot be empty";
			}

			///// Address /////
			if (!this.addressInput == "") {
				this.$refs.addressError.innerText = "";
			} else {
				e.preventDefault();
				this.$refs.addressError.innerText = "Address cannot be empty";
			}
			///// City /////
			if (!this.cityInput == "") {
				this.$refs.cityError.innerText = "";
			} else {
				e.preventDefault();
				this.$refs.cityError.innerText = "City cannot be empty";
			}
			///// Zip Code /////
			if (!this.postalCodeInput == "") {
				this.$refs.postalCodeError.innerText = "";
			} else {
				this.$refs.postalCodeError.innerText = "Postal Code cannot be empty";
			}

			///// Country /////
			if (!this.countryInput == "") {
				this.$refs.countryError.innerText = "";
			} else {
				e.preventDefault();
				this.$refs.countryError.innerText = "Country cannot be empty";
			}

			//Checkbox services

			var ck_box = $('input[name="services"]:checked').length;

			if (ck_box > 0) {
				this.$refs.checkboxServicesError.innerText = "";
			} else {
				e.preventDefault();
				this.$refs.checkboxServicesError.innerText = "Please select at least one service";
			}

			//Checkbox constraints
			var ck_box = $('input[name="constraints"]:checked').length;

			if (ck_box > 0) {
				this.$refs.checkboxConstraintsError.innerText = "";
			} else {
				e.preventDefault();
				this.$refs.checkboxConstraintsError.innerText = "Please select at least one constraint";
			}

		},
	}
})


manageForms.mount('#addHousePage')