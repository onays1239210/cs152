function student(first, last, ID){
	
	this.firstname = first;
	this.lastname = last;
	this.studentID = ID;
	this.display = function(){console.log(this.firstname, this.lastname, this.studentID)};
	
}

var peter = new student("YUkai","huang", 1123);
peter.display();

var studentList = [];
var sizeOfarray = 10;

for(var i = 0; i < sizeOfarray; i++){
	
	studentList.push(new student("","",i));
	
}
for(var i = 0; i < sizeOfarray; i++){
	
	studentList[i].display();
}

student.prototype.graduated = null;

var student1 = {firstname:"aaaa", lastname:"222222", studentID:1234212};
console.log(student1);