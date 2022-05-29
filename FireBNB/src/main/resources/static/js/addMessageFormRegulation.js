const manageForms = Vue.createApp({
    data(){
        return{  
			subjectInput: "",
            subjectError: "",
			contentInput: "",
            contentError: "",
			pn:null,
        }
    },
    methods:{

        addMessage: function(e){
           
			console.log("formControl");

            /////Message subject/////
            if(!this.subjectInput == ""){
            	this.$refs.subjectError.innerText="";
			}else{
				e.preventDefault();
				this.$refs.subjectError.innerText="Subject cannot be empty";
			}
			
			///// Message content /////
            if(!this.contentInput == ""){
            	this.$refs.contentError.innerText="";
			}else{
				e.preventDefault();
				this.$refs.contentError.innerText="Content cannot be empty";
			}
			
        }, 
    }
})


manageForms.mount('#addMessagePage')