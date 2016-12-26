	var delay = 50;
	var isRunning = false;

	function myCheck(element){

		if(element.value == null || element.value =="" || element.value < 0){
			element.setAttribute("style","border:2px solid #f00;");
			return false;
		}
		element.setAttribute("style","border:1px solid #ccc;");
		return true;
		
	}
	
	function random(minId,maxId,show){
		

		if(!myCheck(minId) || !myCheck(maxId)){
			return;
		}
		var min = new Number(minId.value);
		var max = new Number(maxId.value);
		
		if(min > max){
			maxId.setAttribute("style","border:2px solid #f00;");
			return;
		}
		
		if(max.toString() == min.toString()){
			show.innerHTML = min.toString();
			show.setAttribute("style","color:red;font-size: 150px");
			return;
		}
		show.setAttribute("style","color:black;font-size: 150px");
		if(/START/.test(button.value)){
			this.isRunning = true;
			window.value = setTimeout(function(){run(max,min,show)},delay);
			button.setAttribute("value", "STOP!");
			
		}else{
			
			this.isRunning = false;
		}
		
	}
	function run(max,min,show){	
			
		if(isRunning){	
			window.value = setTimeout(function(){run(max,min,show)},delay);
		}else{
			if(delay <=1000){
				button.disabled = true;
				window.value = setTimeout(function(){run(max,min,show)},delay);
				this.delay+=100;
			}else{
				this.delay = 50;
				show.setAttribute("style","color:red;font-size: 150px");
				button.setAttribute("value", "START!");
				button.disabled = false;
			}
		}
		show.innerHTML = Math.floor((Math.random() * (max-min+1))+min);
	}
	