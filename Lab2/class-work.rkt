#lang racket
(struct employee (ssn salary fname lname))
(struct manager (ssn salary fname lname bonus))

(define (lac-wages emp)
  (match emp
    [(struct employee (social sal first last)) sal]
    [(struct manager (social sal first last extra) (+ sal extra))]))

