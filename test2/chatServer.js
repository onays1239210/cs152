var net = require('net');
var eol = require('os').EOL;

var srvr = net.createServer();
var clientList = [];

srvr.on('connection', function(client) {
  client.name = client.remoteAddress + ':' + client.remotePort;
  client.write('Welcome, ' + client.name + eol);
  clientList.push(client);

  client.on('data', function(data) {
    broadcast(data, client);
	
	
	

  });
});

function broadcast(data, client) {
	
    if(data=="\\list")
	{
		for (i = 0; i < clientList.length; i++) 
		{
			client.write("client name: " +clientList[i].name + "\r\n");
		}
	}
	
	var renameC = (data.indexOf("\\rename") > -1)?true:false;
	
	//part 2
	if(renameC)
	{
		var s1 = data.indexOf("<");
		var s2 = data.indexOf(">");
		var newstring = data.toString().substring(s1+1, s2);
		
		client.name = newstring;
		client.write("client name:" + client.name + "\r\n");
		for (i = 0; i < clientList.length; i++) 
		{
			if (client == clientList[i]) 
			{
				clientList[i].name = client.name;
			}
		}
	}
	
	//part3
	
	var privateM = (data.indexOf("\\private") > -1)?true:false;
	
	if(privateM)
	{
		var variable = data.toString().split(" ");
		for(var i=0; i<variable.length; i++)
		{
			client.write("v: " + variable[i] + "\r\n");
		}
		var s1 = variable[0].indexOf("<");
		var s2 = variable[0].indexOf(">");
		var s3 = variable[1].indexOf("<");
		var s4 = variable[1].indexOf(">");
		
		
		var TargetName = variable[0].substring(s1+1, s2);
		var msg = variable[1].substring(s3+1, s4);
		client.write("TargetName: " + TargetName + "\r\n");
		client.write("msg: " + msg + "\r\n");
		
		for (i = 0; i < clientList.length; i++) 
		{    
			if(clientList[i].name == TargetName){
				clientList[i].write(client.name +" says: "+ msg +"\r\n");
			}
			
		}
		
	}else{
		
		for (var i in clientList) {
		    if (client !== clientList[i]) {
		      clientList[i].write(client.name + " says " + data);
		    }
	}
		
  }
}

srvr.listen(9000);
