
const manageMessages = Vue.createApp({
    data(){
        return{  	
			isReceivedHidden:false,
			isSentHidden:false,
        }
    },
    methods:{
        afficherMessages: function(div){
        	if(div == "received"){
				console.log("received");
				this.isReceivedHidden = false; 
				this.isSentHidden = false
				return this.isReceivedHidden, this.isSentHidden;
			}
			if(div="sent"){
				console.log("sent");

				this.isReceivedHidden = true; 
				this.isSentHidden = true
				return this.isReceivedHidden, this.isSentHidden;
			}
        }, 
    }
})


manageMessages.mount('#messagingPage')
