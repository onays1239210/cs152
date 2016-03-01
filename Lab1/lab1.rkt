#lang racket

(define (max-num lst)
  (cond [(= 0 (length lst)) 0]
        [(> (car lst) (max-num (cdr lst))) (car lst)]
        [else (max-num (cdr lst))]))

;; The function counts from 1 to the specified number, returning a string with the result.
;; The rules are:
;;    If a number is divisible by 3 and by 5, instead say "fizzbuzz"
;;    Else if a number is divisible by 3, instead say "fizz"
;;    Else if a number is divisible by 5, instead say "buzz"
;;    Otherwise say the number
(define (fizzbuzz n)
  (print (fizzbuzz1 1 n)))

;; Helper function for fizzbuzz
(define (fizzbuzz1 i n)
  (if (<= i n)
   (if (= 0 (remainder i 3))
      (if (= 0 (remainder i 5))
          (begin
          (display "fizzbuzz ")
          (fizzbuzz1 (+ 1 i) n))
          (begin
          (display "fizz ")
          (fizzbuzz1 (+ 1 i) n)))
      (if (= 0 (remainder i 5))
          (begin
          (display "buzz ")
          (fizzbuzz1 (+ 1 i) n))
          (begin
          (print i)
          (display " ")
          (fizzbuzz1 (+ 1 i) n)))
       )
      (print null)))

(max-num '(1 2 3 4 5))
(max-num '(-5 -3 -2 -13))
(fizzbuzz 21)