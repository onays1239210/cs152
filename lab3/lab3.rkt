#lang racket
;;1
(define (strings-to-nums lst)
  (map string->number lst))
(strings-to-nums '("1" "2"))

;;2
(define (reverse-list lst)
  (foldl cons '() lst))
(reverse-list '(1 2 3))




