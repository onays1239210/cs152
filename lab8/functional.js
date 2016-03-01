var foldl = function (f, acc, array) {
	if(array.length === 0) return acc;
	else return foldl(f, f(acc,array[0]), array.slice(1));
	
}

console.log(foldl(function(x,y){return x+y}, 0, [1,2,3]));

var foldr = function (f, z, array) {
for( i = 0; i < array.length; i++){
		
		acc = f(array[array.length-i-1], acc);
	}
	return acc;
}

console.log(foldl(function(x,y){return x/y}, 1, [2,4,8]));

var map = function (f, array) {
  if(array.length === 0) return [];
  else return [f(array[0])].concat(map(f, array.slice(1)));
}

console.log(map(function(x){return x+x}, [1,2,3,5,7,9,11,13]));


// Write a curry function as we discussed in class.
// Create a `double` method using the curry function
// and the following `mult` function.
function double(x){
	return x*2;
	
}
console.log(double(3));

function mult(x,y) {
  return function(y){
	  return x*y;
  }
}
console.log(mult(3,5));
