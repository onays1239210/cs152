#lang racket
(define (reverse lst)
  (if (empty? lst)
      lst
      (append (reverse (cdr lst))
              (cons (car lst) null))))

(define (reverse-list lst)
  (foldl cons '() lst))

(define (add-two-list lst1 lst2)
  (cond [(empty? lst1) lst2]
        [(empty? lst2) lst1]
        [else (cons (+ (car lst1) (car lst2))
                    (add-two-list (cdr lst1) (cdr lst2)))]))

