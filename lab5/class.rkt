#lang racket
(define (make-adder x)
  (lambda(y) (+ x y)))
(define inc (make-adder 1))

(inc 2)

(define (make-counter)
  (let ([count 0]);;local variable
    (lambda () (set! count (+ count 1));;this will remember the local variable
      count)))
(define my-event (make-counter))
(my-event)
(my-event)

;;closure: a pair ofa  function and tis environment
;;an environment is a mapping of free variables to their values defined outside of the function