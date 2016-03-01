const TARGET_PAT = /(.*):\s*(.*)/,
      PHONY_PAT = /\.PHONY\s*:\s*(.*)/,
      CMD_PAT = /^\t(.*)/,
      COMMENT_PAT = /#.*/,
      ASSIGN_PAT = /\s*(.+?)\s*=\s*(.+)/,
      VAR_PAT = /\$\{(.*?)\}/;

var fs = require('fs'),
    process = require('process'),
    eol = require('os').EOL,
    execSync = require('child_process').execSync,
    targets,
    args,
    makeFileName = "Makefile.txt",
    vars = {};

/**
 * Constructor function for a make target.
 * Every target has a name, a list of dependencies (that is, targets that
 * must be executed before this target), and a list of commands that must
 * be executed when the target is called.
 *
 * A phony target is invoked any time that it is called, unless it has
 * already been invoked for this call to make.  Other targets will be called
 * only when a file of the same name as the target does not exist, or if
 * the timestamp on the file is older than the timestamp of any file dependencies.
 *
 * Any command beginning with a '-' is tolerant of errors; if an error is encountered,
 * subsequent commands should still be executed.  If an error is encountered for
 * other commands, execution should terminate.
 */
function Target(name, dependencies, commands, phony) {
  this.name = name;
  this.dependencies = dependencies || [];
  this.commands = commands || [];
  this.phony = phony || false;
}

// Recursively call every dependency of this target, and then apply
// the commands associated with the target.  You may find it worthwhile
// to add arguments to this function -- feel free to do so.
Target.prototype.invoke = function(arr){
	//name : the name of the target
	//depend: dependencies of the target
	//arr: the whole list of the targets
	//position: the location when going through the arr
	//phony: the pohny of the target
	function recursive(name, depend, arr, position, phony){
	    if(depend.length > (position+1)){
	      recursive(name, depend, arr, position+1, phony);
	    }
	    

	//child = exec('echo %USER%',
	 
	    fs.exists(name, function(exists){
	    	
	    	if(exists && !phony){
	    		fs.stat(name, function(err, stat1) {
	    			  //console.log("test:  "+stat1.ctime.getTime());
	    			  fs.stat(depend[0], function(err, stat2) {
	    				  //console.log("test:  "+stat2.ctime.getTime());
		    			  
	    				  if(stat1.ctime.getTime() < stat2.ctime.getTime()){
	    					  console.log(arr[name].commands[0]);
	    					  execSync(arr[name].commands[0])
		    			  }else{
		    				  console.log("jsmake: '"+name+"' is up to date.");
		    			  }
	    				
		    			  });
	    			  });	
	    	}else if(!depend[0]){
	    		console.log(arr[name].commands[0]);  
	    		var string = execSync(arr[name].commands[0])
	    		console.log(string.toString());
	    	}else if(depend[0]){
	    		
				switch (depend[position]){
					case 'Employee.java':
						 console.log(arr['Employee.class'].commands[0]);
						 return execSync(arr['Employee.class'].commands[0]);	 
						 break;
						 
					case 'Employee.class':
						 console.log(arr['Employee.class'].commands[0]);
						 return execSync(arr['Employee.class'].commands[0]);	 
						 break;
						 
					case 'Company.java':
						 console.log(arr['Company.class'].commands[0]); 
						 return execSync(arr['Company.class'].commands[0]);
						 break;
					default: 
						 return console.log("No action is received");       
						 break;
					}//end switch
					
			}//else 
	    
	    });//end  fs.exits()
	}//end of recursive
	 recursive(this.name, this.dependencies, arr, 0, this.phony);
}//end of invoke


Target.prototype.addCommand = function(cmd) {
	this.commands = cmd.split(/\n/);
}

Target.prototype.getPhony = function() {
	this.phony = true;
}

// Read in a file and return a hash of targets.
function parse(fileName) {
  var i, lines, line, cmd, first, m, deps, target, targetName,
      phonies = [],
      makeTargets = {};
  lines = fs.readFileSync(fileName).toString().split(eol);
  for (i=0; i<lines.length; i++) {
    line = lines[i].replace(COMMENT_PAT, '');
    if (line.match(PHONY_PAT)) {
      phonies = line.match(PHONY_PAT)[1].split(/\s+/);
    } else if (line.match(ASSIGN_PAT)) {
      m = line.match(ASSIGN_PAT);
      vars[m[1]] = m[2];
    } else if (line.match(CMD_PAT)) {
      if (!target) {
        console.error(fileName + ":" + (i+1) +
            ": *** commands commence before first target.  Stop.");
        process.exit(1);
      }
      cmd = line.match(CMD_PAT)[1];
      target.addCommand(cmd);
      //get phony
      	for(var j = 0; j < phonies.length; j++){
      		if(!phonies || phonies[j] == target.name){
      			target.getPhony();
      		}
      	}
    } else if (line.match(TARGET_PAT)) {
      // Add previous target and start a new one
      if (target) makeTargets[target.name] = target;
      m = line.match(TARGET_PAT);
      targetName = m[1];
      deps = m[2].split(/\s+/);
      if (!first) first = targetName;
      target = new Target(targetName, deps);
    }
  }
  if (target) makeTargets[target.name] = target;
  else {
    console.error("jsmake: *** No targets.  Stop.");
    process.exit(1);
  }
  
  return [makeTargets, first];
}

// Handling command-line arguments
args = process.argv
if (args[0].match(/\bnode/)) args.shift(); // Drop node command
args.shift(); // drop script name
if (args[0] === '-f') {
  args.shift(); // drop '-f'
  makeFileName = args.shift();
}

fs.exists(makeFileName, function(exists) {
  var first, res, i, t;
  if (exists) {
    res = parse(makeFileName);
    targets = res[0];
    first = res[1];
    if (args.length === 0) {
      targets[first].invoke(targets);
    } else {
      for (i in args) {
        t = args[i];
        if (targets.hasOwnProperty(t)) targets[t].invoke(targets);
        else console.error("jsmake: *** No rule to make target `" + t + "'.  Stop.");
      }
    }
  } else {
    console.error('jsmake: *** No targets specified and no makefile found.  Stop.');
  }
});



