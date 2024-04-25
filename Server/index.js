var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);
var players = [];
var boxes = [];

server.listen(8080, function(){
	console.log("Server is now running...");
});

io.on('connection', function(socket){
	console.log("Player Connected!");
	socket.emit('socketID', { id: socket.id });
	socket.emit('getPlayers', players);
	socket.emit('getBoxes', boxes);
	
	socket.on('newPlayer',function(data){
		data.id = socket.id;
		socket.broadcast.emit('newPlayer',data);
	    players.push(new player(data.id,data.x,data.y,data.r,data.code));
	});
	
	socket.on('playerMoved',function(data){
		data.id = socket.id;
		socket.broadcast.emit('playerMoved',data);
		for( var i=0 ;i<players.length ;i++){
			if(players[i].id == data.id){
				players[i].x = data.x;
				players[i].y = data.y;
				players[i].r = data.r;
			}
		}
	});
	socket.on('disconnect', function(){
		console.log("Player Disconnected");
		socket.broadcast.emit('playerDisconnected', { id: socket.id });
		if(players.length == 1){
					boxes.splice(0, boxes.length);			
		}
		for(var i = 0; i < players.length; i++){
			if(players[i].id == socket.id){
				players.splice(i, 1);
			}
		}
	});
	socket.on('createBox',function(data){
		socket.broadcast.emit('newBox',data);
		boxes.push(new box(data.Boxid,data.x, data.y,data.r,data.char));
	});
	socket.on('destorBox',function(data){
		socket.broadcast.emit('destorBox',data);
		for(var i = 0; i < boxes.length; i++){
			if(boxes[i].Boxid == socket.Boxid){
				boxes.splice(i, 1);
			}
		}
		});
	socket.on('moveBox',function(data){
		socket.broadcast.emit('moveBox',data);
		for( var i=0 ;i<boxes.length ;i++){
			if(boxes[i].Boxid == data.Boxid){
				boxes[i].x = data.x;
				boxes[i].y = data.y;
				boxes[i].r = data.r;
			}
		}
	});
	
	//players.push(new player(socket.id,0,0,0,1));
	
});

function player(id, x, y, r,code){
	this.id = id;
	this.x = x;
	this.y = y;
	this.r = r;
	this.code=code;
}
function box(Boxid, x, y, r, char) {
	this.Boxid=Boxid;
	this.x=x;
	this.y=y;
	this.r=r;
	this.char=char;
}