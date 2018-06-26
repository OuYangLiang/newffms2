function checkAmount(obj) {
    var oldValue = $(obj).val();
    
    if (isNaN(oldValue)) {
        alert("请输入有效值。");
        $(obj).val("0.00");
        return false;
    }
    
    if(oldValue!="")
        $(obj).val(parseFloat(oldValue).toFixed(2));
    
    return true;
}

function arrContain(arr,value) {
	for(var i = 0; i < arr.length; i++) {
		if(arr[i] == value)
			return true;
	}
	
	return false;
}

function arrRemove(arr,item) {  
	for(var i = 0; i < arr.length; i++) {  
		if(item == arr[i])
			arr.splice(i,1); 
	}  
}

function rowStyle(row, index) {
	var classes = ['success', 'info', 'warning', 'danger'];
    
    if (index % 2 === 0) {
        return {
            classes: classes[(index % 8) / 2]
        };
    }
    return {};
}

function amtFormatter(value) {
    return "¥" + parseFloat(value).toFixed(2);
}