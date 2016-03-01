function Car(make, model, year) {
this.make = make;
this.model = model;
this.year = year;
this.honk = function() { console.log("honk!"); }
}


var car1 = new Car("Chevy", "Nova");
var car2 = new Car("Tesla", "Model S" );

car2.__proto__.display= function() { console.log("Meep!"); }
var car3 =  Car("Ford", "Explorer", 2001); // Forgot to call "new"

car2.display();
car1.display();
delete car1.display;
car1.display();
car3.honk();
console.log(model);
